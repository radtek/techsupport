package com.aisino2.techsupport.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.core.web.PageAction;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
/**
 * 支持单暂停
 * @author hooxin
 *
 */
@Component
@Scope("prototype")
public class PauseAction extends PageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4814784633390304319L;
	private String taskId;
	private SupportTicket st;
	private Tracking tracking;
	
	private int returnNo;
	private String returnMsg;
	
	
	public SupportTicket getSt() {
		return st;
	}
	public void setSt(SupportTicket st) {
		this.st = st;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	 * 支持单暂停
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		return SUCCESS;
	}
}
