package com.onerunsall.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class UnitReq<T> {
	public static Logger logger = Logger.getLogger(UnitReq.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	public Date reqTime = new Date();
	public Date resTime = null;
	public String reqId = sdf.format(reqTime) + RandomStringUtils.randomNumeric(6);

	protected String reqer = "";
	protected String unit = "";
	protected String reqData = "";
	protected String resData = "";

	protected UnitRes<T> unitRes = null;

	protected UnitReq() {
	}

	public UnitReq(String reqer, String unit, Object... params) {
		this.unit = unit;
		this.reqer = reqer;
		for (int i = 0; i < params.length; i++) {
			this.reqData = this.reqData + (i + 1) + "-" + params[i].toString() + " ";
		}
		logger.info(this.reqId + " reqer: " + this.reqer + " " + this.unit + " reqData: " + this.reqData);
	}

	public UnitReq(String unit, Object... params) {
		this.unit = unit;
		for (int i = 0; i < params.length; i++) {
			this.reqData = this.reqData + (i + 1) + "-" + params[i].toString() + " ";
		}
		logger.info(this.reqId + " reqer: " + this.reqer + " " + this.unit + " reqData: " + this.reqData);
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

	public String getReqer() {
		return reqer;
	}

	public String getReqData() {
		return reqData;
	}

	public String getResData() {
		return resData;
	}

	public UnitRes<T> getUnitRes() {
		return unitRes;
	}

	public UnitRes<T> res(Exception e) {
		UnitBreak unitBreak = null;
		if (e instanceof UnitBreak) {
			unitBreak = (UnitBreak) e;
		} else {
			unitBreak = UnitBreak.diy(98);
		}
		this.resTime = new Date();

		unitRes = new UnitRes<T>();
		unitRes.setCode(unitBreak.getCode());
		unitRes.setCodeMsg(unitBreak.getCodeMsg());
		unitRes.setErrParam(unitBreak.getErrParam());
		unitRes.setData(unitBreak.getData());
		unitRes.setReqId(reqId);
		this.resData = unitRes.toString();

		logger.info(this.reqId + " resData: " + this.resData + " takes: "
				+ (this.resTime.getTime() - this.reqTime.getTime()) + "ms");
		return unitRes;
	}
}
