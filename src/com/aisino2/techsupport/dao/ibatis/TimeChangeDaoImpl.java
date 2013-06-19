package com.aisino2.techsupport.dao.ibatis;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.dao.TimeChangeDao;
import com.aisino2.techsupport.domain.TimeChange;

/**
 * 时间改变数据层实现 (ibatis)
 * 
 * @author hooxin
 * 
 */
@Component
public class TimeChangeDaoImpl extends TechSupportBaseDao implements
		TimeChangeDao {

	@Override
	public TimeChange insert(TimeChange timeChange) {
		timeChange.setId(this.getNextID("timechange_id"));
		return (TimeChange) this.insert("TimeChange.insert", timeChange);
	}

	@Override
	public Page getListForPage(Map<String, Object> params, int pageno,
			int pagesize, String sort, String dir) {
		String sCol = "";
		if (dir == null)
			dir = "";
		else if (!dir.equals("asc") && !dir.equals("desc"))
			dir = " asc ";
		if (sort != null) {
			if (sort.equals("0"))
				sCol = " tr.PROCESSOR " + dir;
			else if (sort.equals("1"))
				sCol = " tr.TRACKING_DATE " + dir;
			// 大于1开始就是技术计划时间
			else if (sort.equals("2"))
				sCol = " t.DEV_SCHE_DATE " + dir;
			else if (sort.equals("3")) {
				sCol = " t.DEV_DS_SCHE_DATE " + dir;
			} else if (sort.equals("4")) {
				sCol = " t.DEV_DS_SCHE_DATE " + dir;
			} else if (sort.equals("5"))
				sCol = " t.DEV_DT_SCHE_DATE " + dir;
			else if (sort.equals("6"))
				sCol = " t.PSG_IS_SCHE_DATE " + dir;
			// 从7开始是产品计划时间
			else if (sort.equals("7")) {
				sCol = " t.PSG_SCHE_DATE " + dir;
			} else if (sort.equals("8")) {
				sCol = " t.PSG_DS_SCHE_DATE " + dir;
			} else
				sCol = " t.id ";
		} else {
			sCol = " t.id ";
		}
		params.put("pageSort", sCol);
		return this.queryForPage("TimeChange.getListForPage", params, pageno,
				pagesize);
	}

}
