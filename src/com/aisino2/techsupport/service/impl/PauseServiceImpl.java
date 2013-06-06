package com.aisino2.techsupport.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IPauseService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

@Component
public class PauseServiceImpl implements IPauseService {
	private SupportTicketService stService;
	private WorkflowUtil workflow;
	private TrackingService trackingService;
	
	
	@Resource(name="TrackingServiceImpl")
	public void setTrackingService(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@Resource(name="SupportTicketServiceImpl")
	public void setStService(SupportTicketService stService) {
		this.stService = stService;
	}
	
	@Resource(name="WorkflowUtil")
	public void setWorkflow(WorkflowUtil workflow) {
		this.workflow = workflow;
	}

	@Transactional
	@Override
	public void pause(String taskId, SupportTicket st, Tracking tracking) {
		if(taskId == null)
			throw new RuntimeException("支持单暂停任务ID为空");
		if(st == null || st.getId() == null)
			throw new RuntimeException("支持单暂停支持单ID为空");
		if(tracking == null || tracking.getStId() == null)
			throw new RuntimeException("支持单暂停进展信息或者关联支持单ID为空");
		
		//保存支持单状态为已暂停
		st.setStStatus(Constants.ST_STATUS_PAUSE);
		st.setLastUpdateDate(new Date());
		
		stService.updateSupportTicket(st);
		//暂停的进展信息
		trackingService.insertTracking(tracking);
		
		//流程
		//设置控制暂停控制变量isPause为TRUE
		Map<String,Boolean> var = new HashMap<String,Boolean>();
		var.put("isPause",true);
		workflow.getTaskService().completeTask(taskId,var);
	}

}
