package com.aisino2.techsupport.workflow.assignee;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Role;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.sysadmin.service.IRoleService;
import com.aisino2.sysadmin.service.IUser_roleService;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.service.SupportTicketService;

/**
 * 支持单暂停和支持单恢复人员指派
 * 
 * @author hooxin
 * 
 */
public class PauseRestoreAssignHandler implements AssignmentHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 306636403847824227L;
	private static final Logger log = Logger
			.getLogger(PauseRestoreAssignHandler.class);

	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional
	public void assign(Assignable ass, OpenExecution exec) throws Exception {

		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(ServletActionContext
						.getServletContext());
		SupportTicketService stService = (SupportTicketService) context
				.getBean("SupportTicketServiceImpl");
		IRoleService roleService = (IRoleService) context
				.getBean("roleService");
		IUser_roleService userRoleService = (IUser_roleService) context
				.getBean("user_roleService");

		SupportTicket st = new SupportTicket();
		st.setId((Integer) exec.getVariable("worksheetno"));
		st = stService.getSupportTicket(st);

		// 获取指派单位
		for (Department department : st.getSupportDeptList()) {
			Role role = new Role();
			role.setRolename(Constants.ST_DEPARTCODE_DEPARTMENT_APPROVAL_ROLE_MAP
					.get(department.getDepartcode()));
			try {
				role = (Role) roleService.getListRole(role).get(0);
			} catch (Exception e) {
				log.error(e);
				throw e;
			}

			User_role userRole = new User_role();
			userRole.setRoleid(role.getRoleid());
			List<User_role> userRoleList = userRoleService
					.getListUser_role(userRole);
			// 指派给对应用户
			for (User_role user_role : userRoleList) {
				ass.addCandidateUser(user_role.getUserid().toString());
			}
		}
	}

}
