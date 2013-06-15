package com.aisino2.techsupport.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.dao.TimeChangeDao;
import com.aisino2.techsupport.domain.TimeChange;
import com.aisino2.techsupport.service.ITimeChangeService;

@Component
public class TimeChangeServiceImpl implements ITimeChangeService {

	private TimeChangeDao timeChangeDao;

	@Resource(name = "timeChangeDaoImpl")
	public void setTimeChangeDao(TimeChangeDao timeChangeDao) {
		this.timeChangeDao = timeChangeDao;
	}

	@Override
	public TimeChange insertTimeChange(TimeChange timeChange) {
		return this.timeChangeDao.insert(timeChange);
	}

	@Override
	public Page getListTimeChangePage(TimeChange timeChange, int pageNo,
			int pageSize, String sort, String dir) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", timeChange.getType());
		map.put("tracking", timeChange.getTracking());
		map.put("id", timeChange.getId());
		return this.timeChangeDao.getListForPage(map, pageNo, pageSize, sort,
				dir);
	}

}
