package com.aisino2.techsupport.service;

import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;

/**
 * 支持单恢复
 * @author hooxin
 *
 */
public interface IRestoreService {
	/**
	 * 恢复
	 * @param taskId 任务号
	 * @param st 支持单
	 * @param tracking 进展
	 */
	void restore(String taskId,SupportTicket st, Tracking tracking);
}
