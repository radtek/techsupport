package com.aisino2.techsupport.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jbpm.api.JbpmException;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.stereotype.Component;

import com.aisino2.common.StringUtil;
import com.aisino2.core.dao.Page;
import com.aisino2.core.service.BaseService;
import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.Globalpar;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.service.IDepartmentService;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.sysadmin.service.IGlobalparService;
import com.aisino2.sysadmin.service.IUserService;
import com.aisino2.techsupport.common.CommonUtil;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.WorksheetDao;
import com.aisino2.techsupport.domain.Mail;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.domain.Worksheet;
import com.aisino2.techsupport.service.ApplyService;
import com.aisino2.techsupport.service.ICeApprovalService;
import com.aisino2.techsupport.service.IDeptApprovalService;
import com.aisino2.techsupport.service.MailService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.service.WorksheetService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

/**
 * @author hooxin
 * 
 */
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
	private IDepartmentService departmentService;
	private IGlobalparService globalparService;
	
	/**
	 * 流程服务
	 */
	private WorkflowUtil workflow;
	private CommonUtil util;

	private MailService mailService;

	private Map<String, String> userEmailMap;

	@Resource(name="globalparService")
	public void setGlobalparService(IGlobalparService globalparService) {
		this.globalparService = globalparService;
	}

	public Map<String, String> getUserEmailMap() {
		if (userEmailMap == null) {
			userEmailMap = new HashMap<String, String>();
			Dict_item item = new Dict_item();
			item.setDict_code(Constants.ST_USER_EMAIL_MAP_DICT_CODE);
			List<Dict_item> itemlist = dicItemService.getListDict_item(item);
			for (Dict_item item1 : itemlist) {
				userEmailMap
						.put(item1.getDisplay_name(), item1.getFact_value());
			}
		}
		return userEmailMap;
	}

	@Resource(name = "mailServiceImpl")
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	@Resource(name = "departmentService")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource(name = "CeApprovalServiceImpl")
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
			throws IOException, RuntimeException {
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
		Map<String, String> approvalDepartmentDict = (Map<String, String>) var
				.get("approvalDepartment");
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
			String status = util.getCellString(row.getCell(7));
			if (!StringUtil.isNotEmpty(status))
				throw new RuntimeException("状态为必填");
			Date scheDate = null;
			try {
				scheDate = row.getCell(5).getDateCellValue();
			} catch (Exception e) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					scheDate = sdf.parse(util.getCellString(row.getCell(5)));
				} catch (Exception e1) {
					try {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy年MM月dd日");
						scheDate = sdf
								.parse(util.getCellString(row.getCell(5)));
					} catch (Exception e2) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy/MM/dd");
							scheDate = sdf.parse(util.getCellString(row
									.getCell(5)));
						} catch (Exception e3) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyyMMdd");
							try {
								scheDate = sdf.parse(util.getCellString(row
										.getCell(5)));
							} catch (java.text.ParseException e4) {
								log.debug(e4);
								throw new RuntimeException("计划时间格式正确");
							}
						}
					}
				}
			}
			if (scheDate == null)
				throw new RuntimeException("计划时间为必填");
			// -----------------选填内容------------------
			String approvalContent = util.getCellString(row.getCell(4));
			approvalContent = StringUtil.isNotEmpty(approvalContent) ? approvalContent
					: Constants.ST_DEFAULT_IMPORT_APPROVAL_CONTENT;
			String supportLeaderName = util.getCellString(row.getCell(6));

			// ---------------处理对象数据------------------
			st.setStNo(stNo);
			st.setRegion(regionReverseDict.get(region));
			st.setSupportContent(supportContent);
			st.setStStatus(stStatusReverseDict.get(status));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			String datetext = null;
			Date operateDate = null;
			String serialNumber = null;
			try {
				datetext = stNo.split("-")[1];
				serialNumber = stNo.split("-")[2];
				operateDate = sdf.parse(datetext);
			} catch (Exception e) {
				log.debug(e, e.fillInStackTrace());
				throw new RuntimeException("支持单编号输入错误");
			}
			st.setSerialNumber(Integer.parseInt(serialNumber));

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
				applicant = (User) userService.getListUser(applicant).get(0);
			} catch (Exception e) {
				log.debug(e, e.fillInStackTrace());
				throw new RuntimeException("大区总在系统中不存在");
			}
			st.setApplicant(applicant);

			if (StringUtil.isNotEmpty(supportLeaderName)) {
				String[] supportLeaderNames = supportLeaderName.split(",");
				Set<Department> departmentSet = new HashSet<Department>();
				Set<User> sleaderSet = new HashSet<User>();
				for (String name : supportLeaderNames) {
					User supportLeader = new User();
					supportLeader.setUsername(name);
					try {
						supportLeader = (User) userService.getListUser(
								supportLeader).get(0);
						Department department = supportLeader.getDepartment();
						while (department != null
								&& !approvalDepartmentDict.keySet().contains(
										department.getDepartcode())) {
							department = departmentService
									.getParentDepart(department);
						}
						if (department == null)
							throw new RuntimeException("无法找到符合负责人的审批部门");
						else {
							departmentSet.add(department);
							if (Constants.ST_APPROVAL_DEPARTMENT_CODE_TECH
									.equals(department.getDepartcode())) {
								st.setDevScheDate(scheDate);
							} else if (Constants.ST_APPROVAL_DEPARTMENT_CODE_PRODUCT
									.equals(department.getDepartcode())) {
								st.setPsgScheDate(scheDate);
							}
						}
					} catch (Exception e) {
						log.debug(e, e.fillInStackTrace());
						throw new RuntimeException("支持单负责人在系统中不存在");
					}
					sleaderSet.add(supportLeader);

				}
				st.getLstSupportLeaders().addAll(sleaderSet);
				st.getSupportDeptList().addAll(departmentSet);
			}

			if (Constants.ST_STATUS_WAIT_DEPARTMENT_APPRAVAL.equals(st
					.getStStatus())) {
				Tracking companyApprovalTrack = new Tracking();
				companyApprovalTrack
						.setApprovalCode(Constants.ST_APPR_TYPE_APPR_PASSED);
				companyApprovalTrack.setTrackingDate(operateDate);
				companyApprovalTrack.setNewProcess(approvalContent);
				companyApprovalTrack.setProcessor(approvalUser);
				companyApprovalTrack.setType(Constants.TRACKING_TYPE_CEREPLY);
				st.getTrackList().add(companyApprovalTrack);
			} else if (Constants.ST_STATUS_GOING.equals(st.getStStatus())) {
				Tracking companyApprovalTrack = new Tracking();
				companyApprovalTrack
						.setApprovalCode(Constants.ST_APPR_TYPE_APPR_PASSED);
				companyApprovalTrack.setTrackingDate(operateDate);
				companyApprovalTrack.setNewProcess(approvalContent);
				companyApprovalTrack.setProcessor(approvalUser);
				companyApprovalTrack.setType(Constants.TRACKING_TYPE_CEREPLY);
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

		// 环节导入
		for (SupportTicket st : lstSupportTicket) {
			if (Constants.ST_STATUS_WAIT_COMPANY_APPRAVAL.equals(st
					.getStStatus())) {
				st.setTrackList(null);
				applyService.insertApplyAndGo(st);
			} else if (Constants.ST_STATUS_WAIT_DEPARTMENT_APPRAVAL.equals(st
					.getStStatus())) {
				List<Tracking> trackList = st.getTrackList();
				st.setTrackList(null);
				String piid = applyService.insertApplyAndGo(st);

				for(Tracking tr : trackList)
					tr.setStId(st.getId());
				
				Task ceTask = workflow.getTaskService().createTaskQuery()
						.executionId(piid)
						.activityName(Constants.ST_PROCESS_CE_APPROVAL)
						.uniqueResult();
				ceApprovalService.insertCeApproval(ceTask.getId(), st,
						trackList.get(0));
			} else if (Constants.ST_STATUS_GOING.equals(st.getStStatus())) {
				List<Tracking> trackList = st.getTrackList();
				st.setTrackList(null);
				String piid = applyService.insertApplyAndGo(st);
				
				for(Tracking tr : trackList)
					tr.setStId(st.getId());
				Task ceTask = workflow.getTaskService().createTaskQuery()
						.executionId(piid)
						.activityName(Constants.ST_PROCESS_CE_APPROVAL)
						.uniqueResult();
				ceApprovalService.insertCeApproval(ceTask.getId(), st,
						trackList.get(0));
				Task techTask = workflow
						.getTaskService()
						.createTaskQuery()
						.executionId(piid)
						.activityName(
								Constants.ST_PROCESS_TECH_DEPARTMENT_APPROVAL)
						.uniqueResult();
				if (techTask != null){
					trackList.get(1).setType(Constants.TRACKING_TYPE_HDEVREPLY);
					techDeptApprovalService.insertDeptApproval(
							techTask.getId(), st, trackList.get(1));
				}
				Task productTask = workflow
						.getTaskService()
						.createTaskQuery()
						.executionId(piid)
						.activityName(
								Constants.ST_PROCESS_PRODUCT_DEPARTMENT_APPROVAL)
						.uniqueResult();
				if (productTask != null){
					trackList.get(1).setType(Constants.TRACKING_TYPE_PGMREPLY);
					productDeptApprovalService.insertDeptApproval(
							productTask.getId(), st, trackList.get(1));
				}
			}

			// for import process end
		}
		// import end
	}

	/**
	 * 自动环节提示,匹配1个参数
	 * 
	 * @throws Exception
	 */
	public void promptProcessAuto(SupportTicket st) throws Exception {
		// 根据支持单ID,找到的当前需要提示的环节,然后根据环节填写邮件发送给环节接收者.
		if (st == null || st.getId() == null)
			throw new RuntimeException("自动提示支持单ID为空");
		List<?> ceApprovalTasklist = workflow.getTaskService()
				.createTaskQuery()
				.activityName(Constants.ST_PROCESS_CE_APPROVAL).list();
		TaskImpl showtipTask = null;
		for (TaskImpl task : (List<TaskImpl>) ceApprovalTasklist) {
			int stId = (Integer) workflow.getExecutionService().getVariable(
					task.getExecutionId(), "worksheetno");
			if (stId == st.getId()) {
				showtipTask = task;
				break;
			}
		}

		if (showtipTask != null) {

			Mail mail = new Mail();
			// 读取邮件内容信息
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("mailContent.properties");
			Properties properties = new Properties();
			properties.load(in);
			// 读取邮件配置信息
			Properties mailConfig = new Properties();
			InputStream config = this.getClass().getClassLoader()
					.getResourceAsStream("mailConfig.properties");
			mailConfig.load(config);
			mail.setUser(properties.getProperty("company_send_name"));
			mail.setEmail(properties.getProperty("company_Address"));
			mail.setPassword(properties.getProperty("compnay_password"));
			// mail.setEmail(email);
			mail.setProtocol(mailConfig.getProperty("protocol"));
			mail.setSmtphost(mailConfig.getProperty("smtphost"));
			mail.setHost(mailConfig.getProperty("host"));

			// 获取收件人email
			List<Participation> candidateSet = workflow.getTaskService()
					.getTaskParticipations(showtipTask.getId());

			for (Participation candidate : candidateSet) {
				if (candidate.getType().equals(Participation.CANDIDATE)) {
					User user = new User();
					user.setUserid(Integer.parseInt(candidate.getUserId()));
					user = userService.getUser(user);
					String email = getUserEmailMap().get(user.getUsername());
					String mailContent = properties
							.getProperty("automessage.approval_content");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					mailContent = util.getMsg(
							mailContent,
							new String[] { user.getUsername(),
									sdf.format(new Date()) });
					mailService.sendByDaemon(mail, properties
							.getProperty("automessage.approval_subject"),
							email, null, mailContent, false);
				}
			}

		}
	}

	/**
	 * 自动环节提示,匹配2个参数
	 * 
	 * @param taskId
	 * @param st
	 * @throws Exception
	 */
	public void promptProcessAuto2(String taskId, SupportTicket st)
			throws Exception {
		// 根据历史的TASKID,找出支持单的实例ID,居于实例ID查询当前的任务,再根据环节信息发送邮件给接收者
		if (taskId == null)
			throw new RuntimeException("自动环节提示历史工作单任务号为空");
		if (st == null || st.getId() == null)
			throw new RuntimeException("自动环节提示支持单实体或者支持单ID为空");
		HistoryTask previousTask = workflow.getHistoryService()
				.createHistoryTaskQuery().taskId(taskId).uniqueResult();
		List<Task> currentTaskList = workflow.getTaskService()
				.createTaskQuery().executionId(previousTask.getExecutionId())
				.list();
		for (Task task : currentTaskList) {
			// 部门审批
			if (Constants.ST_PROCESS_TECH_DEPARTMENT_APPROVAL.equals(task
					.getActivityName())
					|| Constants.ST_PROCESS_PRODUCT_DEPARTMENT_APPROVAL
							.equals(task.getActivityName())) {
				Mail mail = new Mail();
				// 读取邮件内容信息
				InputStream in = this.getClass().getClassLoader()
						.getResourceAsStream("mailContent.properties");
				Properties properties = new Properties();
				properties.load(in);
				// 读取邮件配置信息
				Properties mailConfig = new Properties();
				InputStream config = this.getClass().getClassLoader()
						.getResourceAsStream("mailConfig.properties");
				mailConfig.load(config);
				mail.setUser(properties.getProperty("company_send_name"));
				mail.setEmail(properties.getProperty("company_Address"));
				mail.setPassword(properties.getProperty("compnay_password"));
				// mail.setEmail(email);
				mail.setProtocol(mailConfig.getProperty("protocol"));
				mail.setSmtphost(mailConfig.getProperty("smtphost"));
				mail.setHost(mailConfig.getProperty("host"));

				// 获取收件人email
				List<Participation> candidateSet = workflow.getTaskService()
						.getTaskParticipations(task.getId());

				for (Participation candidate : candidateSet) {
					if (candidate.getType().equals(Participation.CANDIDATE)) {
						User user = new User();
						user.setUserid(Integer.parseInt(candidate.getUserId()));
						user = userService.getUser(user);
						String email = getUserEmailMap()
								.get(user.getUsername());
						String mailContent = properties
								.getProperty("automessage.approval_content");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						mailContent = util.getMsg(mailContent, new String[] {
								user.getUsername(), sdf.format(new Date()) });
						mailService.sendByDaemon(mail, properties
								.getProperty("automessage.approval_subject"),
								email, null, mailContent, false);
					}
				}
			}
			// 进展提示/追踪批复
			else if (Constants.ST_PROCESS_TRACKING.equals(task
					.getActivityName())) {
				Mail mail = new Mail();
				// 读取邮件内容信息
				InputStream in = this.getClass().getClassLoader()
						.getResourceAsStream("mailContent.properties");
				Properties properties = new Properties();
				properties.load(in);
				// 读取邮件配置信息
				Properties mailConfig = new Properties();
				InputStream config = this.getClass().getClassLoader()
						.getResourceAsStream("mailConfig.properties");
				mailConfig.load(config);
				mail.setUser(properties.getProperty("company_send_name"));
				mail.setEmail(properties.getProperty("company_Address"));
				mail.setPassword(properties.getProperty("compnay_password"));
				// mail.setEmail(email);
				mail.setProtocol(mailConfig.getProperty("protocol"));
				mail.setSmtphost(mailConfig.getProperty("smtphost"));
				mail.setHost(mailConfig.getProperty("host"));

				// 获取收件人email
				List<Participation> candidateSet = workflow.getTaskService()
						.getTaskParticipations(task.getId());

				for (Participation candidate : candidateSet) {
					if (candidate.getType().equals(Participation.CANDIDATE)) {
						User user = new User();
						user.setUserid(Integer.parseInt(candidate.getUserId()));
						user = userService.getUser(user);
						String email = getUserEmailMap()
								.get(user.getUsername());
						String mailContent = properties
								.getProperty("automessage.tracking_content");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						mailContent = util.getMsg(mailContent, new String[] {
								user.getUsername(), sdf.format(new Date()) });
						mailService.sendByDaemon(mail, properties
								.getProperty("automessage.tracking_subject"),
								email, null, mailContent, false);
					}
				}
			}
			// 反馈确认
			else if (Constants.ST_PROCESS_FEEDBACK.equals(task
					.getActivityName())) {
				Mail mail = new Mail();
				// 读取邮件内容信息
				InputStream in = this.getClass().getClassLoader()
						.getResourceAsStream("mailContent.properties");
				Properties properties = new Properties();
				properties.load(in);
				// 读取邮件配置信息
				Properties mailConfig = new Properties();
				InputStream config = this.getClass().getClassLoader()
						.getResourceAsStream("mailConfig.properties");
				mailConfig.load(config);
				mail.setUser(properties.getProperty("company_send_name"));
				mail.setEmail(properties.getProperty("company_Address"));
				mail.setPassword(properties.getProperty("compnay_password"));
				// mail.setEmail(email);
				mail.setProtocol(mailConfig.getProperty("protocol"));
				mail.setSmtphost(mailConfig.getProperty("smtphost"));
				mail.setHost(mailConfig.getProperty("host"));

				// 获取收件人email
				List<Participation> candidateSet = workflow.getTaskService()
						.getTaskParticipations(task.getId());

				for (Participation candidate : candidateSet) {
					if (candidate.getType().equals(Participation.CANDIDATE)) {
						User user = new User();
						user.setUserid(Integer.parseInt(candidate.getUserId()));
						user = userService.getUser(user);
						String email = getUserEmailMap()
								.get(user.getUsername());
						String mailContent = properties
								.getProperty("automessage.feedback_content");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						mailContent = util.getMsg(mailContent, new String[] {
								user.getUsername(), sdf.format(new Date()) });
						mailService.sendByDaemon(mail, properties
								.getProperty("automessage.feedback_subject"),
								email, null, mailContent, false);
					}
				}
			}
			// 归档
			else if (Constants.ST_PROCESS_ARCHIVE
					.equals(task.getActivityName())) {
				Mail mail = new Mail();
				// 读取邮件内容信息
				InputStream in = this.getClass().getClassLoader()
						.getResourceAsStream("mailContent.properties");
				Properties properties = new Properties();
				properties.load(in);
				// 读取邮件配置信息
				Properties mailConfig = new Properties();
				InputStream config = this.getClass().getClassLoader()
						.getResourceAsStream("mailConfig.properties");
				mailConfig.load(config);
				mail.setUser(properties.getProperty("company_send_name"));
				mail.setEmail(properties.getProperty("company_Address"));
				mail.setPassword(properties.getProperty("compnay_password"));
				// mail.setEmail(email);
				mail.setProtocol(mailConfig.getProperty("protocol"));
				mail.setSmtphost(mailConfig.getProperty("smtphost"));
				mail.setHost(mailConfig.getProperty("host"));

				// 获取收件人email
				List<Participation> candidateSet = workflow.getTaskService()
						.getTaskParticipations(task.getId());

				for (Participation candidate : candidateSet) {
					if (candidate.getType().equals(Participation.CANDIDATE)) {
						User user = new User();
						user.setUserid(Integer.parseInt(candidate.getUserId()));
						user = userService.getUser(user);
						String email = getUserEmailMap()
								.get(user.getUsername());
						String mailContent = properties
								.getProperty("automessage.archive_content");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						mailContent = util.getMsg(mailContent, new String[] {
								user.getUsername(), sdf.format(new Date()) });
						mailService.sendByDaemon(mail, properties
								.getProperty("automessage.archive_subject"),
								email, null, mailContent, false);
					}
				}
			}
		}

	}

	/**
	 * 自动环节提示,匹配3个参数
	 * 
	 * @param taskId
	 * @param st
	 * @param tracking
	 * @throws Exception
	 */
	public void promptProcessAuto3(String taskId, SupportTicket st,
			Tracking tracking) throws Exception {
		// 除了TASKID以外的参数无意义
		promptProcessAuto2(taskId, st);
	}
	
	/**
	 * 对超过7天的没有对支持单做进展填写的负责人进行邮件提示
	 * @throws Exception 
	 */
	public void promptTrackingAuto() throws Exception{
		List<SupportTicket> stList = null;
		Map<String, Object> map = new HashMap<String, Object>();
//		使用最后更新时间
		map.put("use_last_update_day",1);
		
		//最后更新超过时间
		Globalpar st_tracking_update_interval_day = new Globalpar();
		st_tracking_update_interval_day.setGlobalparcode("st_tracking_update_interval_day");
		st_tracking_update_interval_day = globalparService.getGlobalpar(st_tracking_update_interval_day);
		map.put("last_update_day",st_tracking_update_interval_day.getGlobalparvalue());
		//状态
		map.put("stStatus", Constants.ST_STATUS_GOING);
		
		Page page = stService.getListSupportTicketForPage(map, 1, 999999, null, null);
		stList = page.getData();
		
		for(SupportTicket st : stList){
			Mail mail = new Mail();
			// 读取邮件内容信息
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("mailContent.properties");
			Properties properties = new Properties();
			properties.load(in);
			// 读取邮件配置信息
			Properties mailConfig = new Properties();
			InputStream config = this.getClass().getClassLoader()
					.getResourceAsStream("mailConfig.properties");
			mailConfig.load(config);
			mail.setUser(properties.getProperty("company_send_name"));
			mail.setEmail(properties.getProperty("company_Address"));
			mail.setPassword(properties.getProperty("compnay_password"));
			// mail.setEmail(email);
			mail.setProtocol(mailConfig.getProperty("protocol"));
			mail.setSmtphost(mailConfig.getProperty("smtphost"));
			mail.setHost(mailConfig.getProperty("host"));

			// 获取收件人email
			for (User candidate : st.getLstSupportLeaders()) {
					String email = getUserEmailMap()
							.get(candidate.getUsername());
					String mailContent = properties
							.getProperty("automessage.tracking_content");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd");
					mailContent = util.getMsg(mailContent, new String[] {
							candidate.getUsername(),st.getStNo(), sdf.format(new Date()) });
					mailService.sendByDaemon(mail, properties
							.getProperty("automessage.tracking_subject"),
							email, null, mailContent, false);
			}
		}
	}
}
