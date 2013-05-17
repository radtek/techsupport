package com.aisino2.techsupport.workflow.decision;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.Role;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.sysadmin.service.IRoleService;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.service.SupportTicketService;

@Component
public class DepartmentApprovalDecisionHandler implements DecisionHandler {
	private SupportTicketService stService;
	private ApplicationContext context;
	private IRoleService role_service;
	private IDict_itemService dict_item_service;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7397585664840895226L;

	public String decide(OpenExecution execution) {
		Integer worksheet_id= (Integer) execution.getVariable("worksheetno");
		ServletContext sc =  ServletActionContext.getServletContext();
		context = WebApplicationContextUtils.getWebApplicationContext(sc);
		stService = (SupportTicketService) context.getBean("SupportTicketServiceImpl");
		role_service = (IRoleService) context.getBean("roleService");
		dict_item_service = (IDict_itemService) context.getBean("dict_itemService");
		
		SupportTicket st = new SupportTicket();
		st.setId(worksheet_id);
		st = stService.getSupportTicket(st);
		//添加后面需要用到的判断结果
//		execution.setVariable("productDeptApprResult", true);
//		execution.setVariable("techDeptApprResult", true);
		
		Set<String> assign_roles = new HashSet<String>(); 
		//獲取部門審批角色字典項
		Dict_item dictitem = new Dict_item();
		dictitem.setDict_code(Constants.ST_DEPART_APPR_TYPE_DICT_CODE);
		List<Dict_item> deparment_approval_dict_items = dict_item_service.getListDict_item(dictitem);
		for(Department d : st.getSupportDeptList()){
			Role r = new Role();
			r.setDepartid(d.getDepartid());
			List<Role> under_d_roles = role_service.getListRole(r);
			for(Role role_under_d : under_d_roles){
				boolean found = false;
				for(Dict_item di : deparment_approval_dict_items){
					if(role_under_d.getRolename().equals(di.getFact_value())){
						assign_roles.add(di.getDisplay_name());
						found = true;
						break;
					}
				}
				
				if(found){
					break;
				}
			}
		}
		if(assign_roles.size() == 1){
			return assign_roles.iterator().next();
		}
		else{
			return "both";
		}
	}
}
