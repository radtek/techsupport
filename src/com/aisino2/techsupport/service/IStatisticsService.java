package com.aisino2.techsupport.service;

import java.util.List;
import java.util.Map;

import com.aisino2.techsupport.domain.Statistics;

public interface IStatisticsService {
	/**
	 * 按照地区统计
	 * 
	 * @param map
	 * @return
	 */
	List<Statistics> getStatisticsByRegion(Map<String, Object> map);

	/**
	 * 按照部门统计
	 * 
	 * @param map
	 * @return
	 */
	List<Statistics> getStatisticsByDepartment(Map<String, Object> map);

	/**
	 * 按照负责人统计
	 * 
	 * @param map
	 * @return
	 */
	List<Statistics> getStatisticsBySupportLeader(Map<String, Object> map);
}
