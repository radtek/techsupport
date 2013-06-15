package com.aisino2.techsupport.service;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.domain.TimeChange;

public interface ITimeChangeService {
	TimeChange insertTimeChange(TimeChange timeChange);

	Page getListTimeChangePage(TimeChange timeChange, int pageNo, int pageSize,
			String sort, String dir);

}
