package com.aisino2.techsupport.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.core.service.BaseService;
import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.service.IDepartmentService;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.sysadmin.service.IUserService;
import com.aisino2.techsupport.common.CommonUtil;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.ISupportDeptDao;
import com.aisino2.techsupport.dao.ISupportLeaderRelationDao;
import com.aisino2.techsupport.dao.SupportTicketDao;
import com.aisino2.techsupport.domain.Attachment;
import com.aisino2.techsupport.domain.SupportDept;
import com.aisino2.techsupport.domain.SupportLeaderRelation;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IAttachmentService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

/**
 * 
 * 技术支持单服务
 */
@Component("SupportTicketServiceImpl")
public class SupportTicketServiceImpl extends BaseService implements
		SupportTicketService {

	private SupportTicketDao supportTicketDao;
	private static final Logger log = Logger
			.getLogger(SupportTicketService.class);

	private TrackingService trackService;

	private ISupportDeptDao supportDeptDao;

	private IDepartmentService departmentService;

	private ISupportLeaderRelationDao supportLeaderRelationDao;

	// 附件服务, 这里是把关联的附件信息和技术支持单一起保存在数据库里面
	private IAttachmentService attachmentService;

	private IUserService user_service;

	private IDict_itemService dictitem_service;

	private CommonUtil util;

	/**
	 * 流程控制服务
	 */
	private WorkflowUtil workflow;

	@Resource(name = "CommonUtil")
	public void setUtil(CommonUtil util) {
		this.util = util;
	}

	@Resource(name = "WorkflowUtil")
	public void setWorkflow(WorkflowUtil workflow) {
		this.workflow = workflow;
	}

	@Resource(name = "dict_itemService")
	public void setDictitem_service(IDict_itemService dictitem_service) {
		this.dictitem_service = dictitem_service;
	}

	@Resource(name = "userService")
	public void setUser_service(IUserService user_service) {
		this.user_service = user_service;
	}

	public SupportTicketDao getSupportTicketDao() {
		return supportTicketDao;
	}

	@Resource(name = "SupportTicketDaoImpl")
	public void setSupportTicketDao(SupportTicketDao supportTicketDao) {
		this.supportTicketDao = supportTicketDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aisino2.techsupport.service.SupportTicketService#getListSupportTicket
	 * (com.aisino2.techsupport.domain.SupportTicket)
	 */

	public List<SupportTicket> getListSupportTicket(SupportTicket st) {
		List<SupportTicket> list = null;
		try {
			list = this.supportTicketDao.getListSupportTickets(st);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
		return list;
	}

	public SupportTicket getSupportTicket(SupportTicket st) {
		SupportTicket supportTicket = null;
		try {
			if (st.getApplicant() == null)
				st.setApplicant(new User());
			supportTicket = this.supportTicketDao.getSupportTicket(st);
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
		return supportTicket;
	}

	public SupportTicket insertSupportTicket(SupportTicket st) {
		SupportTicket supportTicket = null;
		try {

			supportTicket = this.supportTicketDao.insertSupportTicket(st);

			if (supportTicket.getTrackList() != null
					&& supportTicket.getTrackList().size() > 0)
				for (int i = 0; i < supportTicket.getTrackList().size(); i++) {
					Tracking track = supportTicket.getTrackList().get(i);
					track.setStId(supportTicket.getId());
					trackService.insertTracking(track);
				}

			// 插入单位关联信息
			if (supportTicket.getSupportDeptList() != null
					&& supportTicket.getSupportDeptList().size() > 0) {
				for (Department dept : supportTicket.getSupportDeptList()) {
					if (dept == null)
						continue;
					SupportDept sdept = new SupportDept();
					Department department = new Department();
					department.setDepartcode(dept.getDepartcode());
					department = departmentService.getDepartment(department);
					sdept.setDeptId(department.getDepartid());
					sdept.setStId(supportTicket.getId());

					supportDeptDao.insertSupportDept(sdept);
				}
			}

			// 保存附件信息
			if (supportTicket.getAttachment_list() != null
					&& supportTicket.getAttachment_list().size() > 0) {
				for (Attachment attachment : supportTicket.getAttachment_list()) {
					attachment.setStId(supportTicket.getId());

					// 关联数据到支持单信息
					attachmentService.updateAttachment(attachment);
				}
			}
		} catch (RuntimeException e) {
			log.error(e, e.fillInStackTrace());
			throw e;
		}
		return supportTicket;
	}

	public void updateSupportTicket(SupportTicket st) {
		try {
			if (st == null || st.getId() == null)
				throw new RuntimeException("技术支持单ID为空");

			// 保存支持单信息
			this.supportTicketDao.updateSupportTicket(st);

			// 更新指派关联单位信息
			if (st.getSupportDeptList() != null
					&& st.getSupportDeptList().size() > 0) {
				// 删除以前的指派单位记录，并且重新设置指派单位记录
				SupportDept supportDept = new SupportDept();
				supportDept.setStId(st.getId());
				this.supportDeptDao.removeSupportDept(supportDept);

				for (Department dept : st.getSupportDeptList()) {
					if (dept == null)
						continue;

					SupportDept sdept = new SupportDept();
					Department department = new Department();
					department.setDepartcode(dept.getDepartcode());
					department = departmentService.getDepartment(department);
					sdept.setDeptId(department.getDepartid());
					sdept.setStId(st.getId());
					// //检查是不是已有该部门了，如果没有在执行插入操作
					// SupportDept check_supportDept = null;
					// try{
					// check_supportDept =
					// supportDeptDao.getListSupportDepts(sdept).get(0);
					// }catch (Exception e) {
					// log.debug(e,e.fillInStackTrace());
					// }
					//
					// if(check_supportDept!=null)
					this.supportDeptDao.insertSupportDept(sdept);

				}

			}
			// 添加 技术负责人关联信息
			if (st.getLstSupportLeaders() != null
					&& st.getLstSupportLeaders().size() > 0) {

				// 装载所有审批部门
				Dict_item dict_item = new Dict_item();
				List<Dict_item> department_approval_items = dictitem_service
						.getListDict_item(dict_item);
				List<Department> dept_approval_departments = new ArrayList<Department>();
				for (Dict_item item : department_approval_items) {
					Department approval_department = new Department();
					approval_department.setDepartcode(item.getFact_value());
					approval_department = departmentService
							.getDepartment(approval_department);
					if (approval_department != null) {
						dept_approval_departments.add(approval_department);
					}
				}

				for (User sl : st.getLstSupportLeaders()) {
					if (sl.getUserid() == null || sl.getUserid() == 0)
						continue;

					// 先删除以前的再添加新的负责人

					SupportLeaderRelation check_slrelation = new SupportLeaderRelation();
					check_slrelation.setStId(st.getId());

					sl = user_service.getUser(sl);

					List<Department> old_sl_department_list = new ArrayList<Department>();

					for (Department d : dept_approval_departments) {
						if (sl.getDepartment().getDepartfullcode()
								.contains(d.getDepartfullcode())) {
							old_sl_department_list.add(d);
							old_sl_department_list.addAll(departmentService
									.getAllChildDepart(d));
						}
					}

					for (Department guess_sl_dept : old_sl_department_list) {
						check_slrelation.setDepartid(guess_sl_dept
								.getDepartid());
						this.supportLeaderRelationDao.delete(check_slrelation);
					}

					SupportLeaderRelation slrelation = new SupportLeaderRelation();
					slrelation.setStId(st.getId());
					slrelation.setSupportLeaderId(sl.getUserid());
					// 添加部门ID
					slrelation.setDepartid(sl.getDepartid());

					// SupportLeaderRelation check_slrelation=null;
					// try{
					// check_slrelation =
					// this.supportLeaderRelationDao.query(slrelation).get(0);
					// }catch (Exception e) {
					// log.debug(e,e.fillInStackTrace());
					// }
					// if(check_slrelation==null)
					this.supportLeaderRelationDao.insert(slrelation);
				}
			}

			// 保存附件信息
			if (st.getAttachment_list() != null
					&& st.getAttachment_list().size() > 0) {
				for (Attachment attachment : st.getAttachment_list()) {
					attachment.setStId(st.getId());

					// 关联数据到支持单信息
					attachmentService.updateAttachment(attachment);
				}
			}
		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	public void deleteSupportTicket(SupportTicket st) {
		try {
			if (st == null || st.getId() == null)
				throw new RuntimeException("SupportTicket ID 为空");

			this.supportTicketDao.deleteSupportTicket(st);

		} catch (RuntimeException e) {
			log.error(e);
			throw e;
		}
	}

	public String generateSupportTicketCode() {

		// 河北-20110113-01
		return null;
	}

	@Resource(name = "TrackingServiceImpl")
	public void setTrackService(TrackingService trackService) {
		this.trackService = trackService;
	}

	@Resource(name = "SupportDeptDaoImpl")
	public void setSupportDeptDao(ISupportDeptDao supportDeptDao) {
		this.supportDeptDao = supportDeptDao;
	}

	public Page getListSupportTicketForPage(Map<String, Object> map,
			int pageNo, int pageSize, String sort, String desc) {

		return this.supportTicketDao.getSupportTicketListForPage(map, pageNo,
				pageSize, sort, desc);
	}

	@Resource(name = "departmentService")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource(name = "supportLeaderDaoImpl")
	public void setSupportLeaderRelationDao(
			ISupportLeaderRelationDao supportLeaderRelationDao) {
		this.supportLeaderRelationDao = supportLeaderRelationDao;
	}

	@Resource(name = "attachmentServiceImpl")
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aisino2.techsupport.service.SupportTicketService#
	 * deleteSupportTicketByApplicant
	 * (com.aisino2.techsupport.domain.SupportTicket)
	 */
	public void deleteSupportTicketByApplicant(SupportTicket st) {
		if (st == null || st.getId() == null)
			throw new RuntimeException("支持单删除的支持单ID不能为空");
		st = getSupportTicket(st);
		if (!Constants.ST_STATUS_WAIT_COMPANY_APPRAVAL.equals(st.getStStatus()))
			throw new RuntimeException("只能删除在待公司审批的支持单");
		deleteSupportTicket(st);
		// 处理支持单流程关联的部分,删除活动流程
		// 从当前激活的任务列表中找到这条ST的任务.
		List<Task> tasklist = workflow.getTaskService().createTaskQuery()
				.activityName(Constants.ST_PROCESS_CE_APPROVAL).list();
		for (Task task : tasklist) {
			int st_id = (Integer) workflow.getExecutionService().getVariable(
					task.getExecutionId(), "worksheetno");;
			if (st_id == st.getId()) {
				workflow.getExecutionService().deleteProcessInstanceCascade(
						workflow.getExecutionService().findExecutionById(task.getExecutionId()).getId());
				break;
			}
		}
	}

}
