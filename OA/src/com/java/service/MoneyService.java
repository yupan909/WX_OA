package com.java.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.java.entity.OaMoney;
import com.java.entity.OaMoneyDetail;
import com.java.entity.Query;

public interface MoneyService {
	
	public JSONObject managerList(HttpServletRequest request, Query query) throws Exception;
	
	public JSONObject managerDetailList(HttpServletRequest request, Query query) throws Exception;
	
	public JSONObject managerSave(OaMoney money,HttpServletRequest request);
	
	public JSONObject managerDelete(HttpServletRequest request);
	
	public JSONObject editList(HttpServletRequest request);
	
	public OaMoneyDetail edit(HttpServletRequest request);
	
	
}
