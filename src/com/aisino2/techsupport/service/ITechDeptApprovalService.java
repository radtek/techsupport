package com.aisino2.techsupport.service;

import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;

public interface ITechDeptApprovalService {
	void insertTechDeptApprovalAndGo(String taskId, SupportTicket st,Tracking tracking);
}
