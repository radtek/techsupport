package com.aisino2.techsupport.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Participation;
import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.core.service.BaseService;
import com.aisino2.sysadmin.domain.Department;
import com.aisino2.sysadmin.domain.Dict_item;
import com.aisino2.sysadmin.domain.User;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.sysadmin.service.IDict_itemService;
import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.ISytxDao;
import com.aisino2.techsupport.dao.SupportTicketDao;
import com.aisino2.techsupport.dao.WorksheetDao;
import com.aisino2.techsupport.domain.Sytx;
import com.aisino2.techsupport.domain.Worksheet;
import com.aisino2.techsupport.service.ISytxService;

@Component("SytxServiceImpl")
public class SytxServiceImpl extends BaseService implements ISytxService{

	private ISytxDao sytxDao;
	private IDict_itemService dicItemService;
	private WorksheetDao worksheet_dao;
	private List lPagein = new ArrayList();
	
	 public List getquerySytxCountlist(Map map){
	        return sytxDao.getquerySytxCountlist(map);
	    }
	/*
	 * (non-Javadoc) 获取技术支持单待办工作列表 分页方式
	 * 
	 * @see
	 * com.aisino2.techsupport.service.WorksheetService#getWorksheetTaskForPage
	 * (int, int, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */

	/*	public Page getquerySytxCountlist(int pageNo, int pagesize,
				String assignee, String activity, String candidateUser,
				String slNo, String region, String stNO) {
			Map<String, Object> map = new HashMap<String, Object>();

			if (assignee != null && assignee.trim().length() > 0) {
				// map.put("user", assignee);
				// map.put("type",Participation.OWNER );
				map.put("assignee", assignee);
			}
			if (activity != null && activity.trim().length() > 0) {
				map.put("activity", activity);
			}
			if (candidateUser != null && candidateUser.trim().length() > 0) {
				map.put("user", candidateUser);
				map.put("type", Participation.CANDIDATE);
			}
			if (slNo != null && slNo.trim().length() > 0) {
				map.put("supportLeader", slNo);
				map.put("useSlMap", 1);
			}
			if (region != null && region.trim().length() > 0) {
				map.put("region", region);
			}
			if (stNO != null && stNO.trim().length() > 0) {
				map.put("stNo", stNO);
			}

			Page pagein = sytxDao.getWorksheetTaskForPage(map, pageNo,
					pagesize, null, null);
			List<Worksheet> worksheetList = pagein.getData();

			for (Worksheet sheet : worksheetList) {
				boolean result = true;

				try {
					if (result) {

						sheet.setTaskId(sheet.getTask().getId());

						Dict_item tempDictI = new Dict_item();
						tempDictI
								.setDict_code(Constants.ST_WORKFLOW_NAME_DICT_CODE);
						tempDictI.setFact_value(sheet.getTask().getActivityName());
						sheet.setActivityName(dicItemService
								.getDictItemNameByDcFv(tempDictI));
						sheet.setApplicantName(sheet.getSt().getApplicant() != null ? sheet
								.getSt().getApplicant().getUsername()
								: "");
						// 技术负责人
						String sSupportLeaderName = "";
						for (User sl : sheet.getSt().getLstSupportLeaders())
							sSupportLeaderName += "," + sl.getUsername();
						sSupportLeaderName = sSupportLeaderName.length() > 0 ? sSupportLeaderName
								.substring(1) : sSupportLeaderName;
						sheet.setSupportLeaderName(sSupportLeaderName);

						// 设置单位名称
						String supportDeptName = "";
						for (Department dept : sheet.getSt().getSupportDeptList()) {
							supportDeptName += dept.getDepartname() + ",";
						}
						supportDeptName = supportDeptName.length() > 0 ? supportDeptName
								.substring(0, supportDeptName.length() - 1)
								: supportDeptName;

						sheet.setSupportDeptName(supportDeptName);
						// sheet.setSupportDeptCode(sheet.getSt().getSupportDept()
						// .getDepartcode());
						sheet.setStNo(sheet.getSt().getStNo());

						tempDictI.setDict_code(Constants.ST_REGION_DICT_CODE);
						tempDictI.setFact_value(sheet.getSt().getRegion());
						sheet.setRegionName(dicItemService
								.getDictionaryItemByDictCodeAndFactValue(tempDictI)
								.getDisplay_name());
						sheet.setRegionCode(sheet.getSt().getRegion());

						tempDictI.setDict_code(Constants.ST_STATUS_DICT_CODE);
						tempDictI.setFact_value(sheet.getSt().getStStatus());
						sheet.setStStatusName(dicItemService
								.getDictionaryItemByDictCodeAndFactValue(tempDictI)
								.getDisplay_name());
						sheet.setStStatusCode(sheet.getSt().getStStatus());

					}
				} catch (Exception e) {
					log.error(e);
					log.debug(e.fillInStackTrace());
				}

			}

			// Page pagein = new Page(pageNo, pagesize);
			// pagein.setData(worksheetList);
			// pagein.setTotalRows(worksheetList.size());

			return pagein;
		}*/
	@Resource(name = "SytxDaoImpl")
	public void setSytxDao(ISytxDao sytxDao) {
		this.sytxDao = sytxDao;
	}

	public void setDicItemService(IDict_itemService dicItemService) {
		this.dicItemService = dicItemService;
	}

	public void setWorksheet_dao(WorksheetDao worksheet_dao) {
		this.worksheet_dao = worksheet_dao;
	}

	public List getListUser_roleName(Map map) {
		// TODO Auto-generated method stub
		return sytxDao.getListUser_roleName(map);
	}

}
