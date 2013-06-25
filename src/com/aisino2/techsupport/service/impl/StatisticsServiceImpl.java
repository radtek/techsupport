package com.aisino2.techsupport.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aisino2.techsupport.dao.IStatisticsDao;
import com.aisino2.techsupport.domain.Statistics;
import com.aisino2.techsupport.service.IStatisticsService;

/**
 * 统计服务
 * 
 * @author hooxin
 * 
 */
@Component
public class StatisticsServiceImpl implements IStatisticsService {
	private IStatisticsDao statisticsDao;

	@Resource(name = "statisticsDaoImpl")
	public void setStatisticsDao(IStatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}

	@Override
	public List<Statistics> getStatisticsByRegion(Map<String, Object> map) {
		return statisticsDao.getStatisticsByRegion(map);
	}

	@Override
	public List<Statistics> getStatisticsByDepartment(Map<String, Object> map) {
		return statisticsDao.getStatisticsByDepartment(map);
	}

	@Override
	public List<Statistics> getStatisticsBySupportLeader(Map<String, Object> map) {
		return statisticsDao.getStatisticsBySupportLeader(map);
	}

}
