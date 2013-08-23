package com.aisino2.techsupport.domain;

/**
 * 统计
 * 
 * @author hooxin
 * 
 */
public class Statistics {
	/**
	 * 部门名称
	 */
	private String departname;
	/**
	 * 区域
	 */
	private String region;
	/**
	 * 负责人名称
	 */
	private String stLeaderName;
	/**
	 * 状态待部门审批计数
	 */
	private int statusWaitDepartmentApprovalCount;
	/**
	 * 状态待公司审批计数
	 */
	private int statusWaitCompanyApprovalCount;

	/**
	 * 状态公司审批未通过计数
	 */
	private int statusCompanyApprovalNoPassCount;
	/**
	 * 状态部门审批不通过计数
	 */
	private int statusDepartmentApprovalNoPassCount;
	/**
	 * 状态进行中计数
	 */
	private int statusGoingCount;
	/**
	 * 状态待反馈计数
	 */
	private int statusWaitFeedbackCount;
	/**
	 * 状态反馈计数
	 */
	private int statusFeedbackCount;
	/**
	 * 状态已完成计数
	 */
	private int statusGoneCount;
	/**
	 * 状态已暂停计数
	 */
	private int statusPauseCount;
	/**
	 * 状态异常终止计数
	 */
	private int statusStopCount;

	/**
	 * 机构全码
	 */
	private String departfullcode;

	private String regionName;

	private String parentDepartname;

	private int supportCount;

	public int getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(int supportCount) {
		this.supportCount = supportCount;
	}

	public int getStatusCompanyApprovalNoPassCount() {
		return statusCompanyApprovalNoPassCount;
	}

	public void setStatusCompanyApprovalNoPassCount(
			int statusCompanyApprovalNoPassCount) {
		this.statusCompanyApprovalNoPassCount = statusCompanyApprovalNoPassCount;
	}

	public String getParentDepartname() {
		return parentDepartname;
	}

	public void setParentDepartname(String parentDepartname) {
		this.parentDepartname = parentDepartname;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDepartfullcode() {
		return departfullcode;
	}

	public void setDepartfullcode(String departfullcode) {
		this.departfullcode = departfullcode;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getStLeaderName() {
		return stLeaderName;
	}

	public void setStLeaderName(String stLeaderName) {
		this.stLeaderName = stLeaderName;
	}

	public int getStatusWaitDepartmentApprovalCount() {
		return statusWaitDepartmentApprovalCount;
	}

	public void setStatusWaitDepartmentApprovalCount(
			int statusWaitDepartmentApprovalCount) {
		this.statusWaitDepartmentApprovalCount = statusWaitDepartmentApprovalCount;
	}

	public int getStatusWaitCompanyApprovalCount() {
		return statusWaitCompanyApprovalCount;
	}

	public void setStatusWaitCompanyApprovalCount(
			int statusWaitCompanyApprovalCount) {
		this.statusWaitCompanyApprovalCount = statusWaitCompanyApprovalCount;
	}

	public int getStatusDepartmentApprovalNoPassCount() {
		return statusDepartmentApprovalNoPassCount;
	}

	public void setStatusDepartmentApprovalNoPassCount(
			int statusDepartmentApprovalNoPassCount) {
		this.statusDepartmentApprovalNoPassCount = statusDepartmentApprovalNoPassCount;
	}

	public int getStatusGoingCount() {
		return statusGoingCount;
	}

	public void setStatusGoingCount(int statusGoingCount) {
		this.statusGoingCount = statusGoingCount;
	}

	public int getStatusWaitFeedbackCount() {
		return statusWaitFeedbackCount;
	}

	public void setStatusWaitFeedbackCount(int statusWaitFeedbackCount) {
		this.statusWaitFeedbackCount = statusWaitFeedbackCount;
	}

	public int getStatusFeedbackCount() {
		return statusFeedbackCount;
	}

	public void setStatusFeedbackCount(int statusFeedbackCount) {
		this.statusFeedbackCount = statusFeedbackCount;
	}

	public int getStatusGoneCount() {
		return statusGoneCount;
	}

	public void setStatusGoneCount(int statusGoneCount) {
		this.statusGoneCount = statusGoneCount;
	}

	public int getStatusPauseCount() {
		return statusPauseCount;
	}

	public void setStatusPauseCount(int statusPauseCount) {
		this.statusPauseCount = statusPauseCount;
	}

	public int getStatusStopCount() {
		return statusStopCount;
	}

	public void setStatusStopCount(int statusStopCount) {
		this.statusStopCount = statusStopCount;
	}

}
