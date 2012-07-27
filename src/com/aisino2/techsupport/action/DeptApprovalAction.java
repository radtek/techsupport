package com.aisino2.techsupport.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.core.web.BaseAction;
import com.aisino2.sysadmin.Constants;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.sysadmin.service.IUser_roleService;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.service.IDeptApprovalService;
import com.aisino2.techsupport.service.WorksheetService;

/**
 * 技术支持单 单位审核 环节
 * 
 * 
 */
@Component("DeptApprovalAction")
@Scope("prototype")
public class DeptApprovalAction extends BaseAction {
	private IDeptApprovalService deptApprovalService;

	private WorksheetService worksheetService;

	/**
	 * 支持单任务号
	 */
	private String taskId;

	/**
	 * 回显用支持的实体
	 */
	private SupportTicket st;

	/**
	 * 环节保存用的
	 */
	private SupportTicket deptApprovalSt;

	// 返回状态信息
	/**
	 * 返回状态信息
	 */
	private String returnMsg;
	// 返回状态号
	/**
	 * 返回状态号
	 */
	private int returnNo;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4028170629317506257L;

	/**
	 * spring web context
	 */
	private ApplicationContext context;

	/**
	 * 部門審批類型角色 
	 */
	private String deptApprType;
	
	public String getDeptApprType() {
		return deptApprType;
	}

	public void setDeptApprType(String deptApprType) {
		this.deptApprType = deptApprType;
	}

	/**
	 * 本环节信息初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	public String init() throws Exception {
		st = worksheetService.getWorksheetTask(taskId).getSt();
		return SUCCESS;
	}

	/**
	 * 单位审核 环节保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			ServletContext sc = ServletActionContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(sc);

			if(deptApprType.equals(com.aisino2.techsupport.common.Constants.ST_ROLE_TECH_DEPT_APPR_TYPE)){
				deptApprovalService = (IDeptApprovalService) context.getBean("techDeptApprovalServiceImpl");
			}
			else{
				deptApprovalService = (IDeptApprovalService) context.getBean("productDeptApprovalServiceImpl");
			}
			deptApprovalService.insertDeptApproval(taskId, st, st
					.getTrackList().get(0));
			returnNo = 0;
			returnMsg = "部门审核 保存成功";
		} catch (RuntimeException e) {
			returnNo = 1;
			returnMsg = e.getMessage();
			throw e;
		}
		return SUCCESS;
	}

	@Resource(name = "DeptApprovalServiceImpl")
	public void setDeptApprovalService(IDeptApprovalService deptApprovalService) {
		this.deptApprovalService = deptApprovalService;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public SupportTicket getSt() {
		return st;
	}

	public void setSt(SupportTicket st) {
		this.st = st;
	}

	public SupportTicket getDeptApprovalSt() {
		return deptApprovalSt;
	}

	public void setDeptApprovalSt(SupportTicket deptApprovalSt) {
		this.deptApprovalSt = deptApprovalSt;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public int getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(int returnNo) {
		this.returnNo = returnNo;
	}

	@Resource(name = "WorksheetServiceImpl")
	public void setWorksheetService(WorksheetService worksheetService) {
		this.worksheetService = worksheetService;
	}

}
