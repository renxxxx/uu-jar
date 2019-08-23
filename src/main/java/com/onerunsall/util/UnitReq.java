package com.onerunsall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

public class UnitReq {
	public static Logger logger = Logger.getLogger(UnitReq.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public Date reqTime = new Date();
	public Date resTime = null;
	public String reqId = sdf.format(reqTime) + RandomStringUtils.randomNumeric(6);

	protected String reqName = "";
	protected String reqStr = "";
	protected String resStr = "";

	protected UnitReq() {
	}

	public UnitReq(String reqName, Object... params) {
		this.reqName = reqName;
		for (int i = 0; i < params.length; i++) {
			this.reqStr = this.reqStr + (i + 1) + "-" + params[i].toString() + " ";
		}
		logger.info(this.reqId + " " + this.reqName);
		logger.info(this.reqId + " req : " + this.reqStr);
	}

	public void res() {
		this.resTime = new Date();
		logger.info(this.reqId + " res : " + this.resStr + " takes:" + (this.resTime.getTime() - this.reqTime.getTime())
				+ "ms");
	}

	public void res(Object param) {
		this.resTime = new Date();
		this.resStr = param.toString();
		logger.info(this.reqId + " res : " + this.resStr + " takes : "
				+ (this.resTime.getTime() - this.reqTime.getTime()) + "ms");
	}
}
