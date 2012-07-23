package com.aisino2.techsupport.workflow.assignee;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.aisino2.sysadmin.service.IRoleService;
import com.aisino2.sysadmin.service.IUserService;
import com.aisino2.techsupport.service.SupportTicketService;

public class DepartmentApprovalAssignmentHandler implements AssignmentHandler {
	/**
	 * 部门审批角色类型
	 */
	private String department_approval_type;
	private SupportTicketService st_service;
	private IRoleService role_service;
	private IUserService user_service;
	
	public void setDepartment_approval_type(String department_approval_type) {
		this.department_approval_type = department_approval_type;
	}

	@Override
	public void assign(Assignable arg0, OpenExecution arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
