package com.onerunsall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

public class UnitReq<Q, T> {
	public static Logger logger = Logger.getLogger(UnitReq.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public Date reqTime = new Date();
	public Date resTime = null;
	public String reqId = sdf.format(reqTime) + RandomStringUtils.randomNumeric(6);

	protected String reqer = "";
	protected String unit = "";
	protected Q req = null;
	protected UnitBreak ubreak = null;

	protected UnitReq() {
	}

	public UnitReq(String unit, Q req) {
		this.unit = unit;
		this.req = this.req;
		logger.info(this.reqId + " " + this.unit + " req: " + this.req);
	}

	public Date getReqTime() {
		return reqTime;
	}

	public Date getResTime() {
		return resTime;
	}

	public String getReqId() {
		return reqId;
	}

	public String getUnit() {
		return unit;
	}

	public void setReqer(String reqer) {
		if (this.reqer != null && !this.reqer.isEmpty())
			throw new RuntimeException("设置请求人超过两次");
		this.reqer = reqer;
	}

	public String getReqer() {
		return reqer;
	}

	public T getBreakData() {
		return (T) this.ubreak.getData();
	}

	public UnitBreak getUbreak() {
		return ubreak;
	}

	public void ubreak(Exception e) {
		if (e instanceof UnitBreak) {
			this.ubreak = (UnitBreak) e;
		} else {
			this.ubreak = UnitBreak.diy(98);
		}
		this.resTime = new Date();
		this.ubreak.setReqId(reqId);
		logger.info(this.reqId + " break: " + ubreak.toString() + " takes: "
				+ (this.resTime.getTime() - this.reqTime.getTime()) + "ms");
	}
}
