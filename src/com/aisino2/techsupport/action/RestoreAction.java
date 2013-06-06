package com.aisino2.techsupport.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.core.web.PageAction;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IRestoreService;
import com.aisino2.techsupport.service.SupportTicketService;

/**
 * 支持单恢复
 * 
 * @author hooxin
 * 
 */
@Component
@Scope("prototype")
public class RestoreAction extends PageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6136196080884704448L;
	/**
	 * 任务号
	 */
	private String taskId;
	/**
	 * 支持单信息
	 */
	private SupportTicket st;
	/**
	 * 进展信息
	 */
	private Tracking tracking;
	/**
	 * 返回代号
	 */
	private int returnNo;
	/**
	 * 反馈信息
	 */
	private String returnMsg;

	private IRestoreService restoreService;
	
	@Resource(name="restoreServiceImpl")
	public void setRestoreService(IRestoreService restoreService) {
		this.restoreService = restoreService;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public SupportTicket getSt() {
		return st;
	}

	public void setSt(SupportTicket st) {
		this.st = st;
	}

	public Tracking getTracking() {
		return tracking;
	}

	public void setTracking(Tracking tracking) {
		this.tracking = tracking;
	}

	public int getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(int returnNo) {
		this.returnNo = returnNo;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	/**
	 * 恢复
	 * 
	 * @return
	 */
	public String save() {
		return SUCCESS;
	}
}
