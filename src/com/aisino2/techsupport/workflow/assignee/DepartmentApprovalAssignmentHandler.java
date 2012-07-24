package com.aisino2.techsupport.workflow.assignee;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.sysadmin.domain.Role;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.sysadmin.service.IRoleService;
import com.aisino2.sysadmin.service.IUser_roleService;

/**
 * 部门审批指派处理
 * @author hooxin
 *
 */
public class DepartmentApprovalAssignmentHandler implements AssignmentHandler {
	/**
	 * 部门审批角色类型
	 */
	private String department_approval_type;
	private IRoleService role_service;
	private IUser_roleService user_role_service;
	private ApplicationContext context;
	
	public void setDepartment_approval_type(String department_approval_type) {
		this.department_approval_type = department_approval_type;
	}

	@Override
	public void assign(Assignable assignable, OpenExecution exec) throws Exception {
		Map<String, Object> var = (Map<String, Object>) exec.getVariables();
//		ServletContext sc = (ServletContext) var.get("servletContext");
		ServletContext sc = ServletActionContext.getServletContext();
		context = WebApplicationContextUtils.getWebApplicationContext(sc);
		role_service = (IRoleService) context.getBean("roleService");
		user_role_service  = (IUser_roleService) context.getBean("user_roleService");
		
		Role role = new Role();
		role.setRolename(department_approval_type);
		role = (Role) role_service.getListRole(role).get(0);
		User_role ur = new User_role();
		ur.setRoleid(role.getRoleid());
		List<User_role> ur_list = user_role_service.getListUser_role(ur);
		for(User_role userrole : ur_list){
			assignable.addCandidateGroup(String.valueOf(userrole.getUserid()));
		}
	}

}
