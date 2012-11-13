package com.aisino2.techsupport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.drools.lang.DRLParser.constraint_expression_return;
import org.jbpm.api.JbpmException;
import org.jbpm.api.TaskService;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Component;

import com.aisino2.common.StringUtil;
import com.aisino2.core.dao.Page;
import com.aisino2.core.service.BaseService;
import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.sysadmin.service.IUserService;
import com.aisino2.techsupport.common.CommonUtil;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.WorksheetDao;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.domain.Worksheet;
import com.aisino2.techsupport.service.ApplyService;
import com.aisino2.techsupport.service.ICeApprovalService;
import com.aisino2.techsupport.service.IDeptApprovalService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.service.WorksheetService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

@Component("WorksheetServiceImpl")
public class WorksheetServiceImpl extends BaseService implements
		WorksheetService {
	private IDict_itemService dicItemService;
	private SupportTicketService stService;
	private WorksheetDao worksheet_dao;
	private IUserService userService;
	private ApplyService applyService;
	private IDeptApprovalService techDeptApprovalService;
	private IDeptApprovalService productDeptApprovalService;
	private TrackingService trackingService;
	private ICeApprovalService ceApprovalService;
	/**
	 * 流程服务
	 */
	private WorkflowUtil workflow;
	private CommonUtil util;

	@Resource(name="CeApprovalServiceImpl")
	public void setCeApprovalService(ICeApprovalService ceApprovalService) {
		this.ceApprovalService = ceApprovalService;
	}

	@Resource(name = "techDeptApprovalServiceImpl")
	public void setTechDeptApprovalService(
			IDeptApprovalService techDeptApprovalService) {
		this.techDeptApprovalService = techDeptApprovalService;
	}

	@Resource(name = "productDeptApprovalServiceImpl")
	public void setProductDeptApprovalService(
			IDeptApprovalService productDeptApprovalService) {
		this.productDeptApprovalService = productDeptApprovalService;
	}

	@Resource(name = "TrackingServiceImpl")
	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@Resource(name = "ApplyServiceImpl")
	public void setApplyService(ApplyService applyService) {
		this.applyService = applyService;
	}

	@Resource(name = "userService")
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Resource(name = "CommonUtil")
	public void setUtil(CommonUtil util) {
		this.util = util;
	}

	@Resource(name = "WorksheetDaoImpl")
	public void setWorksheet_dao(WorksheetDao worksheet_dao) {
		this.worksheet_dao = worksheet_dao;
	}

	public List<Worksheet> getWorksheetTaskList(String assignee,
			String activity, String candidateUser, String slNo, String region) {

		return null;
	}

	/*
	 * (non-Javadoc) 获取技术支持单待办工作列表 分页方式
	 * 
	 * @see
	 * com.aisino2.techsupport.service.WorksheetService#getWorksheetTaskForPage
	 * (int, int, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public Page getWorksheetTaskForPage(int pageNo, int pagesize,
			String assignee, String activity, String candidateUser,
			String slNo, String region, String stNO) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (assignee != null && assignee.trim().length() > 0) {
			// map.put("user", assignee);
			// map.put("type",Participation.OWNER );
			map.put("assignee", assignee);
		}
		if (activity != null && activity.trim().length() > 0) {
			map.put("activity", activity);
		}
		if (candidateUser != null && candidateUser.trim().length() > 0) {
			map.put("user", candidateUser);
			map.put("type", Participation.CANDIDATE);
		}
		if (slNo != null && slNo.trim().length() > 0) {
			map.put("supportLeader", slNo);
			map.put("useSlMap", 1);
		}
		if (region != null && region.trim().length() > 0) {
			map.put("region", region);
		}
		if (stNO != null && stNO.trim().length() > 0) {
			map.put("stNo", stNO);
		}

		Page pagein = worksheet_dao.getWorksheetTaskForPage(map, pageNo,
				pagesize, null, null);
		List<Worksheet> worksheetList = pagein.getData();

		for (Worksheet sheet : worksheetList) {
			boolean result = true;

			try {
				if (result) {

					sheet.setTaskId(sheet.getTask().getId());

					Dict_item tempDictI = new Dict_item();
					tempDictI
							.setDict_code(Constants.ST_WORKFLOW_NAME_DICT_CODE);
					tempDictI.setFact_value(sheet.getTask().getActivityName());
					sheet.setActivityName(dicItemService
							.getDictItemNameByDcFv(tempDictI));
					sheet.setApplicantName(sheet.getSt().getApplicant() != null ? sheet
							.getSt().getApplicant().getUsername()
							: "");
					// 技术负责人
					String sSupportLeaderName = "";
					for (User sl : sheet.getSt().getLstSupportLeaders())
						sSupportLeaderName += "," + sl.getUsername();
					sSupportLeaderName = sSupportLeaderName.length() > 0 ? sSupportLeaderName
							.substring(1) : sSupportLeaderName;
					sheet.setSupportLeaderName(sSupportLeaderName);

					// 设置单位名称
					String supportDeptName = "";
					for (Department dept : sheet.getSt().getSupportDeptList()) {
						supportDeptName += dept.getDepartname() + ",";
					}
					supportDeptName = supportDeptName.length() > 0 ? supportDeptName
							.substring(0, supportDeptName.length() - 1)
							: supportDeptName;

					sheet.setSupportDeptName(supportDeptName);
					// sheet.setSupportDeptCode(sheet.getSt().getSupportDept()
					// .getDepartcode());
					sheet.setStNo(sheet.getSt().getStNo());

					tempDictI.setDict_code(Constants.ST_REGION_DICT_CODE);
					tempDictI.setFact_value(sheet.getSt().getRegion());
					sheet.setRegionName(dicItemService
							.getDictionaryItemByDictCodeAndFactValue(tempDictI)
							.getDisplay_name());
					sheet.setRegionCode(sheet.getSt().getRegion());

					tempDictI.setDict_code(Constants.ST_STATUS_DICT_CODE);
					tempDictI.setFact_value(sheet.getSt().getStStatus());
					sheet.setStStatusName(dicItemService
							.getDictionaryItemByDictCodeAndFactValue(tempDictI)
							.getDisplay_name());
					sheet.setStStatusCode(sheet.getSt().getStStatus());

				}
			} catch (Exception e) {
				log.error(e);
				log.debug(e.fillInStackTrace());
			}

		}

		// Page pagein = new Page(pageNo, pagesize);
		// pagein.setData(worksheetList);
		// pagein.setTotalRows(worksheetList.size());

		return pagein;
	}

	// public Page getWorksheetTaskForPage(int pageNo, int pagesize,
	// String assignee, String activity, String candidateUser,
	// String slNo, String region,String stNO) {
	// TaskService taskService = workflow.getTaskService();
	//
	// List<Task> tasklist = taskService.createTaskQuery().assignee(assignee)
	// .candidate(candidateUser).activityName(activity)
	// .page((pageNo - 1) * pagesize, pagesize).orderDesc("id").list();
	// List<Worksheet> worksheetList = new ArrayList<Worksheet>();
	//
	// for (Task task : tasklist) {
	// boolean result = true;
	// Integer stId = (Integer) taskService.getVariable(task.getId(),
	// "worksheetno");
	//
	// SupportTicket st = new SupportTicket();
	// st.setId(stId);
	//
	// st = stService.getSupportTicket(st);
	//
	// try {
	// // 过滤
	// if (stNO != null && stNO.trim().length()>0 && result)
	// result = result && st.getStNo().equals(stNO);
	// if (slNo != null && result){
	// boolean contain_sl = false;
	// for (User sl : st.getLstSupportLeaders()) {
	// if( slNo.equals(sl.getUserid().toString())){
	// contain_sl=true;
	// break;
	// }
	// }
	// result = result && contain_sl;
	// }
	// if (region != null && result)
	// result = result && region.equals(st.getRegion());
	//
	// if (result) {
	// Worksheet sheet = new Worksheet();
	//
	// sheet.setTask(task);
	// sheet.setSt(st);
	//
	// sheet.setTaskId(sheet.getTask().getId());
	//
	// Dict_item tempDictI = new Dict_item();
	// tempDictI.setDict_code(Constants.ST_WORKFLOW_NAME_DICT_CODE);
	// tempDictI.setFact_value(sheet.getTask().getActivityName());
	// sheet.setActivityName(dicItemService.getDictItemNameByDcFv(tempDictI));
	// sheet.setApplicantName(sheet.getSt().getApplicant() != null ? sheet
	// .getSt().getApplicant().getUsername()
	// : "");
	// // 技术负责人
	// String sSupportLeaderName = "";
	// for (User sl : sheet.getSt().getLstSupportLeaders())
	// sSupportLeaderName += "," + sl.getUsername();
	// sSupportLeaderName = sSupportLeaderName.length() > 0 ? sSupportLeaderName
	// .substring(1) : sSupportLeaderName;
	// sheet.setSupportLeaderName(sSupportLeaderName);
	//
	// // 设置单位名称
	// String supportDeptName = "";
	// for (Department dept : sheet.getSt().getSupportDeptList()) {
	// supportDeptName += dept.getDepartname() + ",";
	// }
	// supportDeptName = supportDeptName.length() > 0 ? supportDeptName
	// .substring(0, supportDeptName.length() - 1)
	// : supportDeptName;
	//
	// sheet.setSupportDeptName(supportDeptName);
	// // sheet.setSupportDeptCode(sheet.getSt().getSupportDept()
	// // .getDepartcode());
	// sheet.setStNo(sheet.getSt().getStNo());
	//
	// tempDictI.setDict_code(Constants.ST_REGION_DICT_CODE);
	// tempDictI.setFact_value(sheet.getSt().getRegion());
	// sheet.setRegionName(dicItemService
	// .getDictionaryItemByDictCodeAndFactValue(tempDictI)
	// .getDisplay_name());
	// sheet.setRegionCode(sheet.getSt().getRegion());
	//
	// tempDictI.setDict_code(Constants.ST_STATUS_DICT_CODE);
	// tempDictI.setFact_value(sheet.getSt().getStStatus());
	// sheet.setStStatusName(dicItemService
	// .getDictionaryItemByDictCodeAndFactValue(tempDictI)
	// .getDisplay_name());
	// sheet.setStStatusCode(sheet.getSt().getStStatus());
	// worksheetList.add(sheet);
	//
	// sheet.setTask(null);
	// }
	// } catch (Exception e) {
	// log.error(e);
	// log.debug(e.fillInStackTrace());
	// }
	//
	// }
	//
	// tasklist.clear();
	//
	// Page pagein = new Page(pageNo, pagesize);
	// pagein.setData(worksheetList);
	// pagein.setTotalRows(worksheetList.size());
	//
	// return pagein;
	// }
	public Worksheet getWorksheetTask(String taskId) {
		Worksheet worksheet = null;
		TaskService taskService = workflow.getTaskService();
		if (taskId == null || taskId.trim().length() == 0) {
			log.error("taskId 为空");
			throw new RuntimeException("taskId 为空");
		}
		Task task = taskService.getTask(taskId);
		// ((TaskImpl)task).getAllParticipants().size();
		if (task != null) {

			Integer stId = (Integer) taskService.getVariable(task.getId(),
					"worksheetno");

			SupportTicket st = new SupportTicket();
			st.setId(stId);
			// @fixed 申请人在环节查询的时候为空,任意制定一个APPLICANT的ID以满足SQL 关联查询的条件
			// User applicant=new User();
			// applicant.setUserid(1);
			// st.setApplicant(applicant);
			// @fixed 技术负责人在环节查询的时候为空,任意制定一个负责人的ID以满足SQL 关联查询的条件
			// User sl=new User();
			// sl.setUserid(1);
			// st.setSupportLeader(sl);

			try {
				st = stService.getSupportTicket(st);

				worksheet = new Worksheet();
				worksheet.setTask((TaskImpl) task);
				Dict_item tempDictI = new Dict_item();
				tempDictI.setDict_code(Constants.ST_WORKFLOW_NAME_DICT_CODE);
				tempDictI.setFact_value(worksheet.getTask().getActivityName());
				worksheet.setActivityName(dicItemService
						.getDictItemNameByDcFv(tempDictI));
				worksheet.setTaskId(task.getId());
				worksheet.setFormResourceName(task.getFormResourceName());

				worksheet.setSt(st);
				tempDictI.setDict_code(Constants.ST_REGION_DICT_CODE);
				tempDictI.setFact_value(worksheet.getSt().getRegion());
				worksheet.setRegionCode(st.getRegion());
				worksheet.setRegionName(dicItemService
						.getDictItemNameByDcFv(tempDictI));
				worksheet
						.setApplicantName(worksheet.getSt().getApplicant() != null ? worksheet
								.getSt().getApplicant().getUsername()
								: "");
				// 技术负责人
				String sSupportLeaderName = "";
				for (User sl : worksheet.getSt().getLstSupportLeaders())
					sSupportLeaderName += "," + sl.getUsername();
				sSupportLeaderName = sSupportLeaderName.length() > 0 ? sSupportLeaderName
						.substring(1) : sSupportLeaderName;
				worksheet.setSupportLeaderName(sSupportLeaderName);
				// 设置单位名称
				String supportDeptName = "";
				for (Department dept : worksheet.getSt().getSupportDeptList()) {
					supportDeptName += dept.getDepartname() + ",";
				}
				supportDeptName = supportDeptName.length() > 0 ? supportDeptName
						.substring(0, supportDeptName.length() - 1)
						: supportDeptName;

				worksheet.setSupportDeptName(supportDeptName);
				worksheet.setStNo(worksheet.getSt().getStNo());

				worksheet.setTask(null);
			} catch (Exception e) {
				log.error(e);
				log.debug(e.fillInStackTrace());
			}

		}
		return worksheet;
	}

	@Resource(name = "dict_itemService")
	public void setDicItemService(IDict_itemService dicItemService) {
		this.dicItemService = dicItemService;
	}

	@Resource(name = "SupportTicketServiceImpl")
	public void setStService(SupportTicketService stService) {
		this.stService = stService;
	}

	@Resource(name = "WorkflowUtil")
	public void setWorkflow(WorkflowUtil workflow) {
		this.workflow = workflow;
	}

	public List<Dict_item> get_region_with_userrole(Map map) {
		List<Dict_item> result_list = this.worksheet_dao
				.get_region_with_userrole(map);
		if (!result_list.isEmpty()) {
			return result_list;
		} else {
			result_list = this.worksheet_dao.get_region_with_deptcode(map);
			return result_list;
		}

	}

	public Page get_region_with_userrole_for_page(Map map, int pageno,
			int pagesize, String dir, String sort) {
		Page result = this.worksheet_dao.get_region_with_userrole_for_page(map,
				pageno, pagesize, dir, sort);
		if (result.getData().isEmpty())
			result = this.worksheet_dao.get_region_with_deptcode_for_page(map,
					pageno, pagesize, dir, sort);
		return result;
	}

	@Override
	public void deployWorkflow() {

	}

	@Override
	public void deployWorkflowByXml() {
		try {
			workflow.workflowDeploy();
		} catch (JbpmException e) {
			log.debug(e);
			throw new RuntimeException("该版本的流程图已经发布过了");
		}
	}

	@Override
	public void removeDeployment(String deploy_id) {
		if (deploy_id == null || deploy_id.length() == 0)
			throw new RuntimeException("部署ID为空，删除已部署的流程必须要指定部署ID。");
		workflow.getRepositoryService().deleteDeploymentCascade(deploy_id);
	}

	@Override
	public void startWorkflow(String process_id, Map<String, Object> var) {
		workflow.getExecutionService()
				.startProcessInstanceById(process_id, var);
	}

	/*
	 * (non-Javadoc) 未完成支持单导入
	 * 
	 * @see
	 * com.aisino2.techsupport.service.WorksheetService#importTechSupport(java
	 * .io.File)
	 */
	@Override
	public void importTechSupport(File excelFile, Map<String, Object> var)
			throws IOException {
		if (excelFile == null || !excelFile.exists())
			throw new RuntimeException("导入的excel文件上传失败");
		FileInputStream fis = new FileInputStream(excelFile);
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		fis.close();
		HSSFSheet sheet = wb.getSheetAt(0);
		List<SupportTicket> lstSupportTicket = new ArrayList<SupportTicket>();
		Map<String, String> stStatusDict = (Map<String, String>) var
				.get("stStatus");
		Map<String, String> regionDict = (Map<String, String>) var
				.get("region");

		// 键值颠倒的字典
		Map<String, String> stStatusReverseDict = new HashMap<String, String>();
		for (String key : stStatusDict.keySet())
			stStatusReverseDict.put(stStatusDict.get(key), key);
		Map<String, String> regionReverseDict = new HashMap<String, String>();
		for (String key : regionDict.keySet()) {
			regionReverseDict.put(regionDict.get(key), key);
		}

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			HSSFRow row = sheet.getRow(i);
			SupportTicket st = new SupportTicket();
			// ------------------必填内容------------------
			String stNo = util.getCellString(row.getCell(0));
			if (!StringUtil.isNotEmpty(stNo))
				throw new RuntimeException("支持单编号为必填");
			String region = util.getCellString(row.getCell(1));
			if (!StringUtil.isNotEmpty(region))
				throw new RuntimeException("大区为必填");
			String regionLeaderName = util.getCellString(row.getCell(2));
			if (!StringUtil.isNotEmpty(regionLeaderName))
				throw new RuntimeException("大区总为必填");
			String supportContent = util.getCellString(row.getCell(3));
			if (!StringUtil.isNotEmpty(supportContent))
				throw new RuntimeException("支持单内容为必填");
			String status = util.getCellString(row.getCell(6));
			if (!StringUtil.isNotEmpty(status))
				throw new RuntimeException("状态为必填");
			// -----------------选填内容------------------
			String approvalContent = util.getCellString(row.getCell(4));
			approvalContent = StringUtil.isNotEmpty(approvalContent) ? approvalContent
					: Constants.ST_DEFAULT_IMPORT_APPROVAL_CONTENT;
			String supportLeaderName = util.getCellString(row.getCell(5));

			// ---------------处理对象数据------------------
			st.setStNo(stNo);
			st.setRegion(regionReverseDict.get(region));
			st.setSupportContent(supportContent);
			st.setStStatus(stStatusReverseDict.get(status));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String datetext = null;
			Date operateDate = null;
			try {
				datetext = stNo.split("-")[1];
				operateDate = sdf.parse(datetext);
			} catch (Exception e) {
				log.debug(e, e.fillInStackTrace());
				throw new RuntimeException("支持单编号输入错误");
			}

			User approvalUser = new User();
			approvalUser
					.setUseraccount(Constants.ST_DEFAULT_IMPORT_APPROVAL_USER);
			try {
				approvalUser = userService.getUser(approvalUser);
			} catch (Exception e) {
				log.debug(e, e.fillInStackTrace());
				throw new RuntimeException("公司审批人在系统中不存在");
			}

			User applicant = new User();
			applicant.setUsername(regionLeaderName);
			try {
				approvalUser = (User) userService.getListUser(approvalUser)
						.get(0);
			} catch (Exception e) {
				log.debug(e, e.fillInStackTrace());
				throw new RuntimeException("大区总在系统中不存在");
			}
			st.setApplicant(applicant);

			if (StringUtil.isNotEmpty(supportLeaderName)) {
				String[] supportLeaderNames = supportLeaderName.split(",");
				for (String name : supportLeaderNames) {
					User supportLeader = new User();
					supportLeader.setUsername(name);
					try {
						supportLeader = (User) userService.getListUser(
								supportLeader).get(0);
					} catch (Exception e) {
						log.debug(e, e.fillInStackTrace());
						throw new RuntimeException("支持单负责人在系统中不存在");
					}
					st.getLstSupportLeaders().add(supportLeader);
				}

			}

			if (Constants.ST_STATUS_WAIT_DEPARTMENT_APPRAVAL.equals(st
					.getStStatus())) {
				Tracking companyApprovalTrack = new Tracking();
				companyApprovalTrack
						.setApprovalCode(Constants.ST_APPR_TYPE_APPR_PASSED);
				companyApprovalTrack.setTrackingDate(operateDate);
				companyApprovalTrack.setNewProcess(approvalContent);
				companyApprovalTrack.setProcessor(approvalUser);
				st.getTrackList().add(companyApprovalTrack);
			} else if (Constants.ST_STATUS_GOING.equals(st.getStStatus())) {
				Tracking companyApprovalTrack = new Tracking();
				companyApprovalTrack
						.setApprovalCode(Constants.ST_APPR_TYPE_APPR_PASSED);
				companyApprovalTrack.setTrackingDate(operateDate);
				companyApprovalTrack.setNewProcess(approvalContent);
				companyApprovalTrack.setProcessor(approvalUser);
				Tracking departmentApprovalTrack = new Tracking();
				departmentApprovalTrack
						.setApprovalCode(Constants.ST_APPR_TYPE_APPR_PASSED);
				departmentApprovalTrack.setTrackingDate(operateDate);
				departmentApprovalTrack.setNewProcess(approvalContent);
				departmentApprovalTrack.setProcessor(approvalUser);
				st.getTrackList().add(companyApprovalTrack);
				st.getTrackList().add(departmentApprovalTrack);
			}

			lstSupportTicket.add(st);
			// for end
		}

		//环节导入
		for (SupportTicket st : lstSupportTicket) {
			if (Constants.ST_STATUS_WAIT_COMPANY_APPRAVAL.equals(st
					.getStStatus())) {
				applyService.insertApplyAndGo(st);
			} else if (Constants.ST_STATUS_WAIT_DEPARTMENT_APPRAVAL.equals(st
					.getStStatus())) {
				String piid = applyService.insertApplyAndGo(st);
				Task ceTask = workflow.getTaskService().createTaskQuery()
						.processInstanceId(piid)
						.activityName(Constants.ST_PROCESS_CE_APPROVAL)
						.uniqueResult();
				ceApprovalService.insertCeApproval(ceTask.getId(), st, st.getTrackList().get(0));
			} else if(Constants.ST_STATUS_GOING.equals(st.getStStatus())){
				String piid = applyService.insertApplyAndGo(st);
				Task ceTask = workflow.getTaskService().createTaskQuery()
						.processInstanceId(piid)
						.activityName(Constants.ST_PROCESS_CE_APPROVAL)
						.uniqueResult();
				ceApprovalService.insertCeApproval(ceTask.getId(), st, st.getTrackList().get(0));
				Task techTask = workflow.getTaskService().createTaskQuery()
						.processDefinitionId(piid)
						.activityName(Constants.ST_PROCESS_TECH_DEPARTMENT_APPROVAL)
						.uniqueResult();
				if(techTask!=null)
					techDeptApprovalService.insertDeptApproval(techTask.getId(), st, st.getTrackList().get(1));
				Task productTask = workflow.getTaskService().createTaskQuery()
						.processDefinitionId(piid)
						.activityName(Constants.ST_PROCESS_PRODUCT_DEPARTMENT_APPROVAL)
						.uniqueResult();
				if(productTask!=null)
					productDeptApprovalService.insertDeptApproval(productTask.getId(), st, st.getTrackList().get(1));
			} 
			
			//for import process end
		}
		// import end
	}
}
