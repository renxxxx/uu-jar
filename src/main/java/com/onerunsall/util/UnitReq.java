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

	protected String unit = "";
	protected String req = "";
	protected String res = "";

	UnitRes<T> unitRes = null;

	protected UnitReq() {
	}

	public UnitReq(String unit, Object... params) {
		this.unit = unit;
		for (int i = 0; i < params.length; i++) {
			this.req = this.req + (i + 1) + "-" + params[i].toString() + " ";
		}
		logger.info(this.reqId + " " + this.unit);
		logger.info(this.reqId + " req : " + this.req);
	}

	public UnitRes res(UnitBreak unitBreak) {
		this.resTime = new Date();

		unitRes = new UnitRes<T>();
		unitRes.setCode(unitBreak.getCode());
		unitRes.setCodeMsg(unitBreak.getCodeMsg());
		unitRes.setErrParam(unitBreak.getErrParam());
		unitRes.setData(unitBreak.getData());

		logger.info(this.reqId + " res : " + this.res + " takes:" + (this.resTime.getTime() - this.reqTime.getTime())
				+ "ms");
		return unitRes;
	}
}
