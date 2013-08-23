package com.aisino2.techsupport.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.core.web.PageAction;
import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.service.IDepartmentService;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.sysadmin.service.IUser_roleService;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.domain.Statistics;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.service.IStatisticsService;
import com.aisino2.techsupport.service.WorksheetService;

@Component
@Scope("prototype")
public class StatisticsAction extends PageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8167918490857513269L;

	private SupportTicket supportTicket;
	private Department limitDeparement;
	private String tabledata;
	private String result;
	private int totalrows = 0;
	private int returnNo = 0;
	private String returnMsg;
	private String returnDebugMsg;
	private IDict_itemService dict_item_service;
	private WorksheetService worksheet_service;
	private IUser_roleService user_role_service;
	private IDepartmentService departmentService;
	private List<Statistics> lStatistics = new ArrayList<Statistics>();

	// 地区字典
	private Map<String, String> regionDict;

	private IStatisticsService statisticsService;

	private static final String SESSION_STATISTICS_LIST_TAG = "session_statistics_list_tag";

	@Resource(name = "departmentService")
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource(name = "statisticsServiceImpl")
	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public SupportTicket getSupportTicket() {
		return supportTicket;
	}

	public void setSupportTicket(SupportTicket supportTicket) {
		this.supportTicket = supportTicket;
	}

	public Department getLimitDeparement() {
		return limitDeparement;
	}

	public void setLimitDeparement(Department limitDeparement) {
		this.limitDeparement = limitDeparement;
	}

	public String getTabledata() {
		return tabledata;
	}

	public void setTabledata(String tabledata) {
		this.tabledata = tabledata;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public int getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(int returnNo) {
		this.returnNo = returnNo;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getReturnDebugMsg() {
		return returnDebugMsg;
	}

	public void setReturnDebugMsg(String returnDebugMsg) {
		this.returnDebugMsg = returnDebugMsg;
	}

	@Resource(name = "dict_itemService")
	public void setDict_item_service(IDict_itemService dict_item_service) {
		this.dict_item_service = dict_item_service;
	}

	@Resource(name = "WorksheetServiceImpl")
	public void setWorksheet_service(WorksheetService worksheet_service) {
		this.worksheet_service = worksheet_service;
	}

	@Resource(name = "user_roleService")
	public void setUser_role_service(IUser_roleService user_role_service) {
		this.user_role_service = user_role_service;
	}

	public List<Statistics> getlStatistics() {
		return lStatistics;
	}

	public void setlStatistics(List<Statistics> lStatistics) {
		this.lStatistics = lStatistics;
	}

	public Map<String, String> getRegionDict() {
		if (regionDict == null) {
			Dict_item dict_item = new Dict_item();
			dict_item.setDict_code(Constants.ST_REGION_DICT_CODE);
			List<Dict_item> lDict_items = dict_item_service
					.getListDict_item(dict_item);
			regionDict = new HashMap<String, String>();
			for (Dict_item item : lDict_items) {
				regionDict.put(item.getFact_value(), item.getDisplay_name());
			}
		}
		return regionDict;
	}

	public void setRegionDict(Map<String, String> regionDict) {
		this.regionDict = regionDict;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String querylistStatisticsByRegion() throws Exception {
		try {
			supportTicket = new SupportTicket();
			supportTicket = (SupportTicket) this.setClass(supportTicket, null);
			limitDeparement = new Department();
			limitDeparement = (Department) this.setClass(limitDeparement, null);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("statisticsType", 1);
			map.put("applicantId", supportTicket.getApplicantId());
			map.put("supportLeaderId", supportTicket.getSupportLeaderId());
			map.put("stStatus", supportTicket.getStStatus());
			map.put("region", supportTicket.getRegion());
			map.put("id", supportTicket.getId());
			map.put("applyDateFrom", supportTicket.getApplyDateFrom());
			map.put("applyDateTo", supportTicket.getApplyDateTo());
			// 计划时间
			map.put("scheTimeFrom", supportTicket.getScheTimeFrom());
			map.put("scheTimeTo", supportTicket.getScheTimeTo());
			// 实际时间
			map.put("compTimeFrom", supportTicket.getCompTimeFrom());
			map.put("compTimeTo", supportTicket.getCompTimeTo());

			if (limitDeparement != null
					&& limitDeparement.getDepartcode() != null
					&& limitDeparement.getDepartcode().trim().length() > 0) {
				// 单位筛选
				map.put("limitDeparement", limitDeparement);
			}

			// ++ 默认用户管辖范围
			User current_user = (User) this.getRequest().getSession()
					.getAttribute(com.aisino2.sysadmin.Constants.userKey);
			Map map2 = new HashMap();
			map.put("userid", current_user.getUserid());
			map.put("departcode", current_user.getDepartcode());
			List region_list = worksheet_service.get_region_with_userrole(map2);
			map.put("user_region_list", region_list);

			lStatistics = statisticsService.getStatisticsByRegion(map);
			setTabledataRegion(lStatistics);
			totalrows = lStatistics.size();
			getRequest().getSession().setAttribute(SESSION_STATISTICS_LIST_TAG,
					lStatistics);
		} catch (Exception e) {
			this.returnMsg = "按照区域统计查询错误";
			this.returnNo = -1;
			log.error(e);
			if (log.isDebugEnabled()) {
				log.debug(e, e.fillInStackTrace());
				this.returnDebugMsg = e.getMessage();
			}
			throw e;
		}
		this.result = "success";
		return SUCCESS;
	}

	private void setTabledataRegion(List<Statistics> lData) {
		List<String> lPros = new ArrayList<String>();
		lPros.add("region");
		lPros.add("regionName");
		lPros.add("supportCount");
		lPros.add("statusWaitCompanyApprovalCount");
		lPros.add("statusCompanyApprovalNoPassCount");
		lPros.add("statusWaitDepartmentApprovalCount");
		lPros.add("statusDepartmentApprovalNoPassCount");
		lPros.add("statusGoingCount");
		lPros.add("statusWaitFeedbackCount");
		lPros.add("statusFeedbackCount");
		lPros.add("statusGoneCount");
		lPros.add("statusPauseCount");
		lPros.add("statusStopCount");

		int waitCompanyApprovalCountSum = 0;
		int companyApprovalNoPassCountSum = 0;
		int waitDepartmentApprovalCountSum = 0;
		int departmentApprovalNoPassCountSum = 0;
		int goingCountSum = 0;
		int waitFeedbackConntSum = 0;
		int feedbackCountSum = 0;
		int goneCountSum = 0;
		int pauseCountSum = 0;
		int stopCount = 0;
		int supportCount = 0;
		for (Statistics statistics : lData) {
			statistics.setRegionName(getRegionDict()
					.get(statistics.getRegion()));
			companyApprovalNoPassCountSum += statistics
					.getStatusCompanyApprovalNoPassCount();
			departmentApprovalNoPassCountSum += statistics
					.getStatusDepartmentApprovalNoPassCount();
			waitCompanyApprovalCountSum += statistics
					.getStatusWaitCompanyApprovalCount();
			waitDepartmentApprovalCountSum += statistics
					.getStatusWaitDepartmentApprovalCount();
			goingCountSum += statistics.getStatusGoingCount();
			waitFeedbackConntSum += statistics.getStatusWaitFeedbackCount();
			feedbackCountSum += statistics.getStatusFeedbackCount();
			goneCountSum += statistics.getStatusGoneCount();
			pauseCountSum += statistics.getStatusPauseCount();
			stopCount += statistics.getStatusStopCount();
			//
			// statistics.setSupportCount(statistics.getStatusGoingCount()
			// + statistics.getStatusWaitFeedbackCount()
			// + statistics.getStatusGoneCount()
			// + statistics.getStatusPauseCount()
			// + statistics.getStatusStopCount()
			// + statistics.getStatusCompanyApprovalNoPassCount()
			// + statistics.getStatusDepartmentApprovalNoPassCount()
			// + statistics.getStatusWaitCompanyApprovalCount());
			supportCount += statistics.getSupportCount();
		}
		Statistics statistics = new Statistics();
		statistics.setRegion("合计");
		statistics.setRegionName("合计");
		statistics
				.setStatusCompanyApprovalNoPassCount(companyApprovalNoPassCountSum);
		statistics
				.setStatusDepartmentApprovalNoPassCount(departmentApprovalNoPassCountSum);
		statistics
				.setStatusWaitCompanyApprovalCount(waitCompanyApprovalCountSum);
		statistics
				.setStatusWaitDepartmentApprovalCount(waitDepartmentApprovalCountSum);
		statistics.setStatusGoingCount(goingCountSum);
		statistics.setStatusWaitFeedbackCount(waitFeedbackConntSum);
		statistics.setStatusFeedbackCount(feedbackCountSum);
		statistics.setStatusGoneCount(goneCountSum);
		statistics.setStatusPauseCount(pauseCountSum);
		statistics.setStatusStopCount(stopCount);
		statistics.setSupportCount(supportCount);
		lData.add(statistics);

		List<String> lCols = new ArrayList<String>();

		this.setData(statistics, lData, lPros, lCols);
		this.tabledata = this.getData();
		this.totalrows = this.getTotalrows();
	}

	public String querylistStatisticsByDepartment() throws Exception {
		try {
			supportTicket = new SupportTicket();
			limitDeparement = new Department();
			supportTicket = (SupportTicket) this.setClass(supportTicket, null);
			limitDeparement = (Department) this.setClass(limitDeparement, null);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("statisticsType", 2);
			map.put("applicantId", supportTicket.getApplicantId());
			map.put("supportLeaderId", supportTicket.getSupportLeaderId());
			map.put("stStatus", supportTicket.getStStatus());
			map.put("region", supportTicket.getRegion());
			map.put("id", supportTicket.getId());
			map.put("applyDateFrom", supportTicket.getApplyDateFrom());
			map.put("applyDateTo", supportTicket.getApplyDateTo());
			// 计划时间
			map.put("scheTimeFrom", supportTicket.getScheTimeFrom());
			map.put("scheTimeTo", supportTicket.getScheTimeTo());
			// 实际时间
			map.put("compTimeFrom", supportTicket.getCompTimeFrom());
			map.put("compTimeTo", supportTicket.getCompTimeTo());

			if (limitDeparement != null
					&& limitDeparement.getDepartcode() != null
					&& limitDeparement.getDepartcode().trim().length() > 0) {
				// 单位筛选
				map.put("limitDeparement", limitDeparement);
			}

			// ++ 默认用户管辖范围
			User current_user = (User) this.getRequest().getSession()
					.getAttribute(com.aisino2.sysadmin.Constants.userKey);
			Map map2 = new HashMap();
			map.put("userid", current_user.getUserid());
			map.put("departcode", current_user.getDepartcode());
			List region_list = worksheet_service.get_region_with_userrole(map2);
			map.put("user_region_list", region_list);

			lStatistics = statisticsService.getStatisticsByDepartment(map);
			setTabledataDepartment(lStatistics);
			totalrows = lStatistics.size();
			getRequest().getSession().setAttribute(SESSION_STATISTICS_LIST_TAG,
					lStatistics);
		} catch (Exception e) {
			this.returnNo = -1;
			this.returnMsg = "按照部门统计查询错误";
			log.error(e);
			if (log.isDebugEnabled()) {
				log.debug(e, e.fillInStackTrace());
				this.returnDebugMsg = e.getMessage();
			}
			throw e;
		}
		this.result = SUCCESS;
		return SUCCESS;
	}

	private void setTabledataDepartment(List<Statistics> lData) {
		List<String> lPros = new ArrayList<String>();
		lPros.add("departname");
		lPros.add("departname");
		lPros.add("supportCount");
		lPros.add("statusDepartmentApprovalNoPassCount");
		lPros.add("statusWaitDepartmentApprovalCount");
		lPros.add("statusGoingCount");
		lPros.add("statusWaitFeedbackCount");
		lPros.add("statusFeedbackCount");
		lPros.add("statusGoneCount");
		lPros.add("statusPauseCount");
		lPros.add("statusStopCount");

		int departmentApprovalNoPassCountSum = 0;
		int waitDepartmentApprovalCountSum = 0;
		int goingCountSum = 0;
		int waitFeedbackConntSum = 0;
		int feedbackCountSum = 0;
		int goneCountSum = 0;
		int pauseCountSum = 0;
		int stopCount = 0;
		int supportCount = 0;

		for (Statistics statistics : lData) {

			departmentApprovalNoPassCountSum += statistics
					.getStatusDepartmentApprovalNoPassCount();
			waitDepartmentApprovalCountSum += statistics
					.getStatusWaitDepartmentApprovalCount();
			goingCountSum += statistics.getStatusGoingCount();
			waitFeedbackConntSum += statistics.getStatusWaitFeedbackCount();
			feedbackCountSum += statistics.getStatusFeedbackCount();
			goneCountSum += statistics.getStatusGoneCount();
			pauseCountSum += statistics.getStatusPauseCount();
			stopCount += statistics.getStatusStopCount();

			// statistics.setSupportCount(statistics.getStatusGoingCount()
			// + statistics.getStatusWaitFeedbackCount()
			// + statistics.getStatusFeedbackCount()
			// + statistics.getStatusGoneCount()
			// + statistics.getStatusPauseCount()
			// + statistics.getStatusStopCount()
			// + statistics.getStatusDepartmentApprovalNoPassCount()
			// + statistics.getStatusWaitDepartmentApprovalCount());

			supportCount += statistics.getSupportCount();
		}
		Statistics statistics = new Statistics();
		statistics.setDepartname("合计");
		statistics
				.setStatusDepartmentApprovalNoPassCount(departmentApprovalNoPassCountSum);
		statistics
				.setStatusWaitDepartmentApprovalCount(waitDepartmentApprovalCountSum);
		statistics.setStatusGoingCount(goingCountSum);
		statistics.setStatusWaitFeedbackCount(waitFeedbackConntSum);
		statistics.setStatusFeedbackCount(feedbackCountSum);
		statistics.setStatusGoneCount(goneCountSum);
		statistics.setStatusPauseCount(pauseCountSum);
		statistics.setStatusStopCount(stopCount);
		statistics.setSupportCount(supportCount);
		lData.add(statistics);

		List<String> lCols = new ArrayList<String>();

		this.setData(statistics, lData, lPros, lCols);
		this.tabledata = this.getData();
	}

	public String querylistStatisticsBySupportLeader() throws Exception {
		try {
			supportTicket = new SupportTicket();
			limitDeparement = new Department();
			supportTicket = (SupportTicket) this.setClass(supportTicket, null);
			limitDeparement = (Department) this.setClass(limitDeparement, null);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("statisticsType", 3);
			map.put("applicantId", supportTicket.getApplicantId());
			map.put("supportLeaderId", supportTicket.getSupportLeaderId());
			map.put("stStatus", supportTicket.getStStatus());
			map.put("region", supportTicket.getRegion());
			map.put("id", supportTicket.getId());
			map.put("applyDateFrom", supportTicket.getApplyDateFrom());
			map.put("applyDateTo", supportTicket.getApplyDateTo());
			// 计划时间
			map.put("scheTimeFrom", supportTicket.getScheTimeFrom());
			map.put("scheTimeTo", supportTicket.getScheTimeTo());
			// 实际时间
			map.put("compTimeFrom", supportTicket.getCompTimeFrom());
			map.put("compTimeTo", supportTicket.getCompTimeTo());

			if (limitDeparement != null
					&& limitDeparement.getDepartcode() != null
					&& limitDeparement.getDepartcode().trim().length() > 0) {
				// 单位筛选
				map.put("limitDeparement", limitDeparement);
			}

			// ++ 默认用户管辖范围
			User current_user = (User) this.getRequest().getSession()
					.getAttribute(com.aisino2.sysadmin.Constants.userKey);
			Map map2 = new HashMap();
			map.put("userid", current_user.getUserid());
			map.put("departcode", current_user.getDepartcode());
			List region_list = worksheet_service.get_region_with_userrole(map2);
			map.put("user_region_list", region_list);

			lStatistics = statisticsService.getStatisticsBySupportLeader(map);
			setTabledataSupportLeader(lStatistics);
			totalrows = lStatistics.size();
			getRequest().getSession().setAttribute(SESSION_STATISTICS_LIST_TAG,
					lStatistics);
		} catch (Exception e) {
			this.returnNo = -1;
			this.returnMsg = "按照支持负责人统计查询错误";
			log.error(e);
			if (log.isDebugEnabled()) {
				log.debug(e, e.fillInStackTrace());
				this.returnDebugMsg = e.getMessage();
			}
			throw e;
		}
		this.result = SUCCESS;
		return SUCCESS;
	}

	private void setTabledataSupportLeader(List<Statistics> lData) {
		List<String> lPros = new ArrayList<String>();
		lPros.add("departname");
		lPros.add("parentDepartname");
		lPros.add("departname");
		lPros.add("stLeaderName");
		lPros.add("supportCount");
		lPros.add("statusWaitDepartmentApprovalCount");
		lPros.add("statusGoingCount");
		lPros.add("statusWaitFeedbackCount");
		lPros.add("statusFeedbackCount");
		lPros.add("statusGoneCount");
		lPros.add("statusPauseCount");
		lPros.add("statusStopCount");

		int waitDepartmentApprovalCountSum = 0;
		int goingCountSum = 0;
		int waitFeedbackConntSum = 0;
		int feedbackCountSum = 0;
		int goneCountSum = 0;
		int pauseCountSum = 0;
		int stopCount = 0;
		int supportCount = 0;

		Map<String, String> map = new HashMap<String, String>();
		Dict_item dict_item = new Dict_item();
		dict_item.setDict_code(Constants.ST_QUERY_DEPARTMENT_DICTCODE);
		List<Dict_item> dict_items = dict_item_service
				.getListDict_item(dict_item);
		for (Dict_item item : dict_items) {
			Department department = new Department();
			department.setDepartcode(item.getFact_value());
			department = departmentService.getDepartment(department);
			map.put(department.getDepartfullcode(), department.getDepartname());
		}
		for (Statistics statistics : lData) {

			waitDepartmentApprovalCountSum += statistics
					.getStatusWaitDepartmentApprovalCount();
			goingCountSum += statistics.getStatusGoingCount();
			waitFeedbackConntSum += statistics.getStatusWaitFeedbackCount();
			feedbackCountSum += statistics.getStatusFeedbackCount();
			goneCountSum += statistics.getStatusGoneCount();
			pauseCountSum += statistics.getStatusPauseCount();
			stopCount += statistics.getStatusStopCount();

			// statistics.setSupportCount(statistics
			// .getStatusWaitDepartmentApprovalCount()
			// + statistics.getStatusGoingCount()
			// + statistics.getStatusWaitFeedbackCount()
			// + statistics.getStatusFeedbackCount()
			// + statistics.getStatusGoneCount()
			// + statistics.getStatusPauseCount()
			// + statistics.getStatusStopCount());
			supportCount += statistics.getSupportCount();

			for (String key : map.keySet()) {
				if (statistics.getDepartfullcode().contains(key)) {
					statistics.setParentDepartname(map.get(key));
					break;
				}
			}
		}
		Statistics statistics = new Statistics();
		statistics.setParentDepartname("合计");
		statistics
				.setStatusWaitDepartmentApprovalCount(waitDepartmentApprovalCountSum);
		statistics.setStatusGoingCount(goingCountSum);
		statistics.setStatusWaitFeedbackCount(waitFeedbackConntSum);
		statistics.setStatusFeedbackCount(feedbackCountSum);
		statistics.setStatusGoneCount(goneCountSum);
		statistics.setStatusPauseCount(pauseCountSum);
		statistics.setStatusStopCount(stopCount);
		statistics.setSupportCount(supportCount);
		lData.add(statistics);

		List<String> lCols = new ArrayList<String>();

		this.setData(statistics, lData, lPros, lCols);
		this.tabledata = this.getData();
	}

	public String querySessionStatisticsData() {
		lStatistics = (List<Statistics>) getRequest().getSession()
				.getAttribute(SESSION_STATISTICS_LIST_TAG);

		this.result = SUCCESS;
		return SUCCESS;
	}
}
