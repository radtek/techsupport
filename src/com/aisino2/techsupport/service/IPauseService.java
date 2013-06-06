package com.aisino2.techsupport.service;

import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;

/**
 * 支持单暂停
 * @author hooxin
 *
 */
public interface IPauseService {
	/**
	 * 暂停
	 * @param taskId
	 * @param st
	 * @param tracking
	 */
	void pause(String taskId,SupportTicket st,Tracking tracking);
}
