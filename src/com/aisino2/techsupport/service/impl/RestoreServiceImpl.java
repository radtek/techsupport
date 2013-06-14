package com.aisino2.techsupport.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.dao.TimeChangeDao;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.TimeChange;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IRestoreService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

/**
 * 支持单恢复
 * 
 * @author hooxin
 * 
 */
@Component
public class RestoreServiceImpl implements IRestoreService {
	private SupportTicketService stService;
	private TrackingService trackingService;
	private WorkflowUtil workflow;
	private TimeChangeDao timeChangeDao;

	@Resource(name = "TimeChangeDaoImpl")
	public void setTimeChangeDao(TimeChangeDao timeChangeDao) {
		this.timeChangeDao = timeChangeDao;
	}

	@Resource(name = "SupportTicketServiceImpl")
	public void setStService(SupportTicketService stService) {
		this.stService = stService;
	}

	@Resource(name = "TrackingServiceImpl")
	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@Resource(name = "WorkflowUtil")
	public void setWorkflow(WorkflowUtil workflow) {
		this.workflow = workflow;
	}

	@Transactional
	@Override
	public void restore(String taskId, SupportTicket st, Tracking tracking,
			TimeChange timeChange) {
		if (taskId == null)
			throw new RuntimeException("支持单恢复的任务号为空");
		if (st == null || st.getId() == null)
			throw new RuntimeException("支持单恢复支持单ID为空");
		if (tracking == null || tracking.getStId() == null)
			throw new RuntimeException("支持单恢复进展信息或者关联支持单ID为空");
		// 保存支持单信息
		// 修改 状态为进行中
		st.setStStatus(Constants.ST_STATUS_GOING);
		stService.updateSupportTicket(st);
		tracking = trackingService.insertTracking(tracking);

		timeChange.setDevDtScheDate(st.getDevDtScheDate());
		timeChange.setDevDsScheDate(st.getDevDdScheDate());
		timeChange.setDevDdScheDate(st.getDevDdScheDate());
		timeChange.setPsgDsScheDate(st.getPsgDsScheDate());
		timeChange.setPsgIsScheDate(st.getPsgIsScheDate());
		timeChange.setPsgScheDate(st.getPsgScheDate());
		timeChange.setDevScheDate(st.getDevScheDate());
		timeChange.setTracking(tracking);

		timeChangeDao.insert(timeChange);
		// 流程
		workflow.getTaskService().completeTask(taskId);
	}

}
