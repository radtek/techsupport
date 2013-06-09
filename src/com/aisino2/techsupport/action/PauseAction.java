package com.aisino2.techsupport.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.core.web.PageAction;
import com.aisino2.techsupport.domain.SupportTicket;
import com.aisino2.techsupport.domain.Tracking;
import com.aisino2.techsupport.service.IPauseService;

/**
 * 支持单暂停
 * 
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
	private IPauseService pauseService;

	@Resource(name = "pauseServiceImpl")
	public void setPauseService(IPauseService pauseService) {
		this.pauseService = pauseService;
	}

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
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		try {
			if (st == null)
				throw new RuntimeException("支持单暂停工单传输错误");
			if (taskId == null)
				throw new RuntimeException("支持单暂停任务号为空");
			pauseService.pause(taskId, st, tracking);
			returnNo = 0;
		} catch (Exception e) {
			log.error(e);
			returnNo = -1;
			returnMsg = "支持单暂停错误";
			if (log.isDebugEnabled()) {
				log.debug(e, e.fillInStackTrace());
				returnMsg = e.getMessage();
			}
		}
		return SUCCESS;
	}
}
