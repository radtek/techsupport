package com.aisino2.techsupport.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.aisino2.techsupport.common.Constants;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IDeptApprovalService;
import com.aisino2.techsupport.service.SupportTicketService;
import com.aisino2.techsupport.service.TrackingService;
import com.aisino2.techsupport.workflow.WorkflowUtil;

public class TechDeptApprovalServiceImpl implements IDeptApprovalService {
	private SupportTicketService stService;
	private TrackingService trackingService;

	/**
	 * 流程控制服务
	 */
	private WorkflowUtil workflow;

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

	@Override
	public void insertDeptApproval(String taskId, SupportTicket st,
			Tracking tracking) {
		if (Constants.ST_APPR_TYPE_APPR_NOPASS.equals(tracking
				.getApprovalCode())) {
			st.setStStatus(Constants.ST_STATUS_DEPARTMENT_APPRAVAL_NOPASS);
		}
		// 更新最后操作时间
		st.setLastUpdateDate(new Date());
		// 保存技术支持单信息
		stService.updateSupportTicket(st);
		// 保存部门审核意见信息
		trackingService.insertTracking(tracking);
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("techDeprtApprovalResult", Constants.ST_APPR_TYPE_APPR_PASSED
				.equals(tracking.getApprovalCode()) ? true : false);
		workflow.getTaskService().completeTask(taskId, var);
	}

}
