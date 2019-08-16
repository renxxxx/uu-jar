package com.giveup;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.giveup.Goback;
import com.giveup.JdbcUtils;
import com.giveup.ResBean;
import com.giveup.ServletUtils;

public class UnitReq {
	public static Logger logger = Logger.getLogger(UnitReq.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	protected Date reqTime = new Date();
	protected Date resTime = null;
	protected String reqId = sdf.format(reqTime) + RandomStringUtils.randomNumeric(6);

	protected String reqSign = "";
	protected String reqStr = "";
	protected String resStr = "";

	public UnitReq(String reqSign, Object... params) {
		this.reqSign = reqSign;
		for (int i = 0; i < params.length; i++) {
			this.reqStr = this.reqStr + (i + 1) + "-" + params[i].toString() + " ";
		}
		logger.info(this.reqSign + " " + this.reqId + " " + this.reqStr);
	}

	public void res(Object param) {
		this.resTime = new Date();
		this.resStr = param.toString();
		logger.info(this.reqSign + " " + this.reqId + " " + this.resStr + " takes:"
				+ (this.resTime.getTime() - this.reqTime.getTime()) + "ms");
	}
}
