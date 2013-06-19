package com.aisino2.techsupport.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aisino2.core.dao.Page;
import com.aisino2.core.web.PageAction;
import com.aisino2.techsupport.domain.TimeChange;
import com.aisino2.techsupport.service.ITimeChangeService;

/**
 * 时间改变轨迹
 * 
 * @author hooxin
 * 
 */
@Component
@Scope("prototype")
public class TimeChangeAction extends PageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5565655714189593846L;

	private TimeChange timeChange;
	private List<TimeChange> timeChangeList;
	private Integer returnNo;
	private String returnMsg;
	private String returnMsgDebug;
	private ITimeChangeService timeChangeService;
	private String tabledata;
	private String result;
	private int totalrows = 0;

	public String getTabledata() {
		return tabledata;
	}

	public void setTabledata(String tabledata) {
		this.tabledata = tabledata;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public String getReturnMsgDebug() {
		return returnMsgDebug;
	}

	public void setReturnMsgDebug(String returnMsgDebug) {
		this.returnMsgDebug = returnMsgDebug;
	}

	public TimeChange getTimeChange() {
		return timeChange;
	}

	public void setTimeChange(TimeChange timeChange) {
		this.timeChange = timeChange;
	}

	public List<TimeChange> getTimeChangeList() {
		return timeChangeList;
	}

	public void setTimeChangeList(List<TimeChange> timeChangeList) {
		this.timeChangeList = timeChangeList;
	}

	public Integer getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(Integer returnNo) {
		this.returnNo = returnNo;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Resource(name = "timeChangeServiceImpl")
	public void setTimeChangeService(ITimeChangeService timeChangeService) {
		this.timeChangeService = timeChangeService;
	}

	/**
	 * 查询时间变更列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String querylist() {
		try {
			Page pager = timeChangeService.getListTimeChangePage(timeChange,
					pagesize, pagerow, sort, dir);
			timeChangeList = pager.getData();
			this.totalrows = pager.getTotalRows();
			this.totalpage = pager.getTotalPages();
			setTabledata(timeChangeList);
			this.result = SUCCESS;
		} catch (Exception e) {
			returnNo = -1;
			returnMsg = "时间变更列表查询失败";
			log.error(e);
			if (log.isDebugEnabled()) {
				log.debug(e, e.fillInStackTrace());
				returnMsgDebug = e.getMessage();
			}
		}
		return SUCCESS;
	}

	/**
	 * 设置时间变更列表的表格显示数据
	 * 
	 * @param lData
	 */
	private void setTabledata(List<TimeChange> lData) {
		List<String> lPro = new ArrayList<String>();
		lPro.add("id");
		lPro.add("trackingPersonName");
		lPro.add("trackingDate");

		lPro.add("psgScheDate");
		lPro.add("psgDsScheDate");

		lPro.add("devScheDate");
		lPro.add("devDsScheDate");
		lPro.add("devDdScheDate");
		lPro.add("devDtScheDate");
		lPro.add("psgIsScheDate");
		lPro.add("trackingContent");

		List<List<String>> lCol = new ArrayList<List<String>>();

		for (TimeChange timeChange : lData) {
			timeChange.setTrackingContent(timeChange.getTracking()
					.getNewProcess());
			timeChange.setTrackingDate(timeChange.getTracking()
					.getTrackingDate());
			timeChange.setTrackingPersonName(timeChange.getTracking()
					.getProcessor().getUsername());
		}
		if (!lData.isEmpty())
			lData.get(0).setTrackingContent("初次审批");
		this.setData(timeChange, lData, lPro, lCol);
		this.tabledata = this.getData();
		totalrows = this.getTotalrows();
	}
}
