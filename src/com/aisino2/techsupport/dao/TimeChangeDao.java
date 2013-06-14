package com.aisino2.techsupport.dao;

import java.util.Map;

import com.aisino2.core.dao.Page;
import com.aisino2.techsupport.domain.TimeChange;

/**
 * 时间改变数据层
 * 
 * @author hooxin
 * 
 */
public interface TimeChangeDao {
	/**
	 * 插入
	 * 
	 * @param timeChange
	 * @return
	 */
	TimeChange insert(TimeChange timeChange);

	/**
	 * 分页查询
	 * 
	 * @param params
	 *            条件
	 * @param pageno
	 *            页数
	 * @param pagesize
	 *            每页显示数
	 * @param sort
	 *            排序序号
	 * @param dir
	 *            升降序
	 * @return 分页元素
	 */
	Page getListForPage(Map<String, Object> params, int pageno, int pagesize,
			String sort, String dir);
}
