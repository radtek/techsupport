package com.aisino2.techsupport.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 时间改变
 * 
 * @author hooxin
 * 
 */
public class TimeChange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216489742251115417L;

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 关联进展信息
	 */
	private Tracking tracking;
	/**
	 * 技术计划时间
	 */
	private Date devScheDate;
	/**
	 * 产品计划时间
	 */
	private Date psgScheDate;
	/**
	 * 技术设计计划时间
	 */
	private Date devDsScheDate;
	/**
	 * 技术实现计划时间
	 */
	private Date devDdScheDate;
	/**
	 * 技术测试计划时间
	 */
	private Date devDtScheDate;
	/**
	 * 产品设计计划时间
	 */
	private Date psgDsScheDate;
	/**
	 * 技术实施计划时间
	 */
	private Date psgIsScheDate;
	/**
	 * 改变时间类型: 0 产品 1 技术
	 */
	private String type;

	// 查询用
	/**
	 * 进展填写人名称
	 */
	private String trackingPersonName;
	/**
	 * 进展填写时间
	 */
	private Date trackingDate;
	/**
	 * 进展信息或者变更原因
	 */
	private String trackingContent;

	public String getTrackingContent() {
		return trackingContent;
	}

	public void setTrackingContent(String trackingContent) {
		this.trackingContent = trackingContent;
	}

	public Tracking getTracking() {
		return tracking;
	}

	public void setTracking(Tracking tracking) {
		this.tracking = tracking;
	}

	public String getTrackingPersonName() {
		return trackingPersonName;
	}

	public void setTrackingPersonName(String trackingPersonName) {
		this.trackingPersonName = trackingPersonName;
	}

	public Date getTrackingDate() {
		return trackingDate;
	}

	public void setTrackingDate(Date trackingDate) {
		this.trackingDate = trackingDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDevScheDate() {
		return devScheDate;
	}

	public void setDevScheDate(Date devScheDate) {
		this.devScheDate = devScheDate;
	}

	public Date getPsgScheDate() {
		return psgScheDate;
	}

	public void setPsgScheDate(Date psgScheDate) {
		this.psgScheDate = psgScheDate;
	}

	public Date getDevDsScheDate() {
		return devDsScheDate;
	}

	public void setDevDsScheDate(Date devDsScheDate) {
		this.devDsScheDate = devDsScheDate;
	}

	public Date getDevDdScheDate() {
		return devDdScheDate;
	}

	public void setDevDdScheDate(Date devDdScheDate) {
		this.devDdScheDate = devDdScheDate;
	}

	public Date getDevDtScheDate() {
		return devDtScheDate;
	}

	public void setDevDtScheDate(Date devDtScheDate) {
		this.devDtScheDate = devDtScheDate;
	}

	public Date getPsgDsScheDate() {
		return psgDsScheDate;
	}

	public void setPsgDsScheDate(Date psgDsScheDate) {
		this.psgDsScheDate = psgDsScheDate;
	}

	public Date getPsgIsScheDate() {
		return psgIsScheDate;
	}

	public void setPsgIsScheDate(Date psgIsScheDate) {
		this.psgIsScheDate = psgIsScheDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
