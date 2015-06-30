package com.aisino2.techsupport.dao.ibatis;

import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.aisino2.core.dao.BaseDao;
import com.aisino2.core.dao.Page;
import com.aisino2.sysadmin.domain.User_role;
import com.aisino2.techsupport.dao.ISytxDao;
import com.aisino2.techsupport.dao.SupportTicketDao;
import com.aisino2.techsupport.dao.WorksheetDao;
import com.aisino2.techsupport.domain.Sytx;

@Component("SytxDaoImpl")
public class SytxDaoImpl extends TechSupportBaseDao implements  ISytxDao {


		/*public Page getWorksheetTaskForPage(Map<String, Object> map, int pageno, int pagesize, String dir, String sort){
			String sCol="";
			if(sort == null)
				sort = "";
			else if(!sort.equals("asc") && !sort.equals("desc"))
				sort = " asc ";
			if (dir!=null){
				if(dir.equals("0"))
					sCol = " s.ST_NO "+ sort;
				else if(dir.equals("1"))
					sCol = " s.region "+ sort;
				else if(dir.equals("2"))
					sCol = " s.applicant "+ sort;
				else if(dir.equals("3"))
					sCol = " sl.sl_id "+ sort;
				else if(dir.equals("4"))
					sCol = " sd.DEPT_ID "+ sort;
				else if(dir.equals("5"))
					sCol = " s.st_status "+ sort;
				else if(dir.equals("6"))
					sCol = " t.name_ "+ sort;
				else
					sCol = " s.ST_NO ";
			}else{
				sCol=" s.ST_NO ";
			}
			map.put("pageSort", sCol);
			return this.queryForPage("sytx.getlistForPageCount", map, pageno, pagesize);
		}*/
	@SuppressWarnings("unchecked")
		public List getquerySytxCountlist(Map map) {
			// TODO Auto-generated method stub
		//int dd = (Integer) queryForObject("sytx.getlistForPageCount", map);
		
		List		list1=	queryForList("sytx.getquerySytxCountlist",map);
					return list1;
		}

	public List getListUser_roleName(Map map) {
		// TODO Auto-generated method stub
		List roleNameList=	queryForList("sytx.getListUser_roleName",map);
		return roleNameList;
	}

}
