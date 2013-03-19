package com.aisino2.techsupport.workflow.assignee;

import java.util.Map;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

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

//	private ApplicationContext context;
//	private SupportTicketService st_service;
//	private IDepartmentService department_service;
//	private IUserService user_service;
//	private ServletContext sc;
	@Override
	public void assign(Assignable assignable, OpenExecution exec) throws Exception {
//		context = WebApplicationContextUtils.getWebApplicationContext(sc);
//		st_service = (SupportTicketService) context.getBean("SupportTicketServiceImpl");
//		department_service = (IDepartmentService) context.getBean("departmentService");
//		user_service = (IUserService) context.getBean("userService");
		Map<String, Object> var = (Map<String, Object>) exec.getVariables();
		for(String key : var.keySet()){
			if(key.contains("departApprUserId__")){
				assignable.addCandidateUser(String.valueOf(var.get(key)));
			}
		}
	}

}
