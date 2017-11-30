package com.java.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.java.common.Result;
import com.java.entity.OaApproveBuy;
import com.java.entity.OaApproveFinance;
import com.java.entity.Query;

public interface ApproveService {
	
	public JSONObject managerList(HttpServletRequest request, Query query);
	
	public JSONObject editList(HttpServletRequest request);
	
	public OaApproveBuy editBuy(HttpServletRequest request);
	
	public JSONObject saveBuy(OaApproveBuy approveBuy,HttpServletRequest request);
	
	public OaApproveFinance editFinance(HttpServletRequest request);
	
	public JSONObject saveFinance(OaApproveFinance approveFinance,HttpServletRequest request);
	
	public JSONObject delete(HttpServletRequest request);
	
	
}
