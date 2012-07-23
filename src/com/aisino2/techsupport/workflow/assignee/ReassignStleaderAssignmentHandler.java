package com.aisino2.techsupport.workflow.assignee;

import javax.servlet.ServletContext;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.sysadmin.service.IDepartmentService;
import com.aisino2.sysadmin.service.IUserService;
import com.aisino2.techsupport.service.SupportTicketService;

/**
 * 重新选择负责人指派监听
 * @author hooxin
 *
 */
public class ReassignStleaderAssignmentHandler implements AssignmentHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9156961200654075794L;

	private ApplicationContext context;
	private SupportTicketService st_service;
	private IDepartmentService department_service;
	private IUserService user_service;
	private ServletContext sc;
	@Override
	public void assign(Assignable assignable, OpenExecution exec) throws Exception {
		context = WebApplicationContextUtils.getWebApplicationContext(sc);
		st_service = (SupportTicketService) context.getBean("SupportTicketServiceImpl");
		department_service = (IDepartmentService) context.getBean("departmentService");
		user_service = (IUserService) context.getBean("userService");
		
	}

}
