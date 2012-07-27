package com.aisino2.techsupport.workflow.assignee;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.sysadmin.domain.User;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.service.SupportTicketService;

/**
 * 追踪批复人员指派
 * @author hooxin
 *
 */
public class TrackingAssignmentHandler implements AssignmentHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6006774398647741472L;
	private ApplicationContext context;
	private SupportTicketService st_service;
	private Integer worksheet_id;
	
	
	public void setWorksheet_id(Integer worksheet_id) {
		this.worksheet_id = worksheet_id;
	}


	@Override
	public void assign(Assignable assignale, OpenExecution exec) throws Exception {
		ServletContext servletContext = ServletActionContext.getServletContext();
		context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		st_service = (SupportTicketService) context.getBean("SupportTicketServiceImpl");
		
		SupportTicket st = new SupportTicket();
		st.setId(worksheet_id);
		
		st = st_service.getSupportTicket(st);
		
		for(User user : st.getLstSupportLeaders()){
			assignale.addCandidateUser(String.valueOf(user.getUserid()));
		}
		
	}

}
