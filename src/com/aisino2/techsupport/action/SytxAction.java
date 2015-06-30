package com.aisino2.techsupport.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jbpm.api.task.Participation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.common.PageUtil;
import com.aisino2.core.dao.Page;
import com.aisino2.core.web.PageAction;
import com.aisino2.sysadmin.Constants;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.techsupport.domain.Sytx;
import com.aisino2.techsupport.domain.Worksheet;
import com.aisino2.techsupport.service.ISytxService;
import com.aisino2.techsupport.service.WorksheetService;

@Component("SytxAction")
@Scope("prototype")
/**
 * 待办工作单
 * 
 * @return
 * @throws Exception
 */
public class SytxAction extends PageAction{

	private WorksheetService worksheetService;
	private ISytxService sytxService;
	private int totalrows = 0;
	private List lSytx = new ArrayList();
	private List lRoleNameList = new ArrayList();
	

	private String tabledata;
	private String result;
	private String assignee;
	private String rolename;




	public String getRolename() {
		return rolename;
	}



	public void setRolename(String rolename) {
		this.rolename = rolename;
	}



	/**
	 * 待办工作单
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String querySytxCount() throws Exception {
		
		HttpServletRequest request = this.getRequest();
		HttpSession session = request.getSession();

		/*Worksheet worksheet = new Worksheet();
		worksheet = (Worksheet) this.setClass(worksheet, null);

		if(worksheet.getActivityName()==null || worksheet.getActivityName().trim().length() == 0)
			worksheet.setActivityName(null);
		if(worksheet.getApplicantName()==null || worksheet.getApplicantName().trim().length() == 0)
			worksheet.setApplicantName(null);
		if(worksheet.getStStatusCode()==null || worksheet.getStStatusCode().trim().length() == 0)
			worksheet.setStStatusCode(null);
		if(worksheet.getRegionCode()==null || worksheet.getRegionCode().trim().length() == 0)
			worksheet.setRegionCode(null);*/
		
		User curuser = (User) session.getAttribute(Constants.userKey);
/*		Sytx sytx = new Sytx();
		sytx = (Sytx)this.setClass(sytx, null);*/
		Map map = new HashMap();
/*		map.put("fkr",sytx.getFkr());
		map.put("gsspr", sytx.getGsspr());
		map.put("cpbspr", sytx.getCpbspr());
		map.put("jsbspr", sytx.getJsbspr());
		map.put("fzr", sytx.getFzr());
		map.put("zlr", sytx.getZlr());*/
		map.put("user", curuser.getUserid());
		map.put("type", Participation.CANDIDATE);
		/*if(curuser.getDepartid()==500000102){
			map.put("activityName_", "tech_department_appr");
			map.put("flag", 1);
		}
		if(curuser.getDepartid()==500000101){
			map.put("activityName_", "product_department_appr");
			map.put("flag", 1);
		}*/
		
		lRoleNameList = sytxService.getListUser_roleName(map);
		for(int i = 0; i<lRoleNameList.size();i++){
			Sytx sytx = new Sytx();
			sytx =  (Sytx) lRoleNameList.get(i);
			rolename = sytx.getRolename();
			if("ts_技术部审批人".equals(rolename)){
				map.put("activityName_", "tech_department_appr");
				map.put("flag", 1);
			}
			if("ts_产品部审批人".equals(rolename)){
				map.put("activityName_", "product_department_appr");
				map.put("flag", 0);
			}
		}
		//if(rolename=="ts_技术部审批人"){

		/*Page taskPage = sytxService.getWorksheetTaskForPage(pagesize,
				pagerow, null, worksheet.getActivityName(),
				String.valueOf(curuser.getUserid()),
				worksheet.getSupportLeaderId()==null?null:String.valueOf(worksheet.getSupportLeaderId()), worksheet.getRegionCode(),worksheet.getStNo());*/

		
		 lSytx = sytxService.getquerySytxCountlist(map);
		//setTabledata(lSytx);
		/*totalpage = taskPage.getTotalPages();
		totalrows = taskPage.getTotalRows();
		lSytx  = taskPage.getData();
		setTabledata(lSytx);*/
		this.result = "success";
		return SUCCESS;
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setTabledata(List lData) throws Exception {
		/*List lPro = new ArrayList();
		lPro.add("taskId");
		lPro.add("stNo");
		lPro.add("regionName");
		lPro.add("applicantName");
		lPro.add("supportLeaderName");
		lPro.add("supportDeptName");
		lPro.add("stStatusName");
		lPro.add("activityName");

		List lCol = new ArrayList();

		if (PageUtil.checkFunction("ts_process")) {
			List lModify = new ArrayList();
			lModify.add("setProcess");
			lModify.add("处理");
			lCol.add(lModify);
		}
		if (PageUtil.checkFunction("ts_sms")) {
			List lDel = new ArrayList();
			lDel.add("sendSMS");
			lDel.add("短信");
			lCol.add(lDel);
		}

		Worksheet worksheet = new Worksheet();
		this.setData(worksheet, lData, lPro, lCol);
		this.tabledata = this.getData();
		totalrows = this.getTotalrows();*/
		List lPro=new ArrayList();
		
		
		lPro.add("tiaoshu");
		lPro.add("status");
		lPro.add("fkr");
		lPro.add("gsspr");
		lPro.add("cpbspr");
		lPro.add("jsbspr");
		lPro.add("fzr");
		lPro.add("zlr");
		
		List lCol=new ArrayList();
		
		Sytx getSytx=new Sytx();
		
	    this.setData(getSytx,lData,lPro,lCol);
	    this.tabledata=this.getData();
	    totalrows=this.getTotalrows();
	}
	public void setWorksheetService(WorksheetService worksheetService) {
		this.worksheetService = worksheetService;
	}
	@Resource(name = "SytxServiceImpl")
	public void setSytxService(ISytxService sytxService) {
		this.sytxService = sytxService;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public void setlSytx(List lSytx) {
		this.lSytx = lSytx;
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

	/*public WorksheetService getWorksheetService() {
		return worksheetService;
	}*/

	/*public ISytxService getSytxService() {
		return sytxService;
	}*/

	public int getTotalrows() {
		return totalrows;
	}

	public List getlSytx() {
		return lSytx;
	}

	public String getTabledata() {
		return tabledata;
	}
	public List getlRoleNameList() {
		return lRoleNameList;
	}



	public void setlRoleNameList(List lRoleNameList) {
		this.lRoleNameList = lRoleNameList;
	}
	
}
