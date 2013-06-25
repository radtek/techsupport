package com.aisino2.techsupport.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aisino2.techsupport.dao.IStatisticsDao;
import com.aisino2.techsupport.domain.Statistics;

/**
 * 统计
 * 
 * @author hooxin
 * 
 */
@Component
public class StatisticsDaoImpl extends TechSupportBaseDao implements
		IStatisticsDao {

	@Override
	public List<Statistics> getStatisticsByRegion(Map<String, Object> map) {
		return this.queryForList("Statistics.byRegion", map);
	}

	@Override
	public List<Statistics> getStatisticsByDepartment(Map<String, Object> map) {
		return this.queryForList("Statistics.byDepartment", map);
	}

	@Override
	public List<Statistics> getStatisticsBySupportLeader(Map<String, Object> map) {
		return this.queryForList("Statistics.bySupportLeader", map);
	}

}
