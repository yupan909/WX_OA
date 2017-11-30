package com.java.service;

import javax.servlet.http.HttpServletRequest;

import com.java.entity.OaNotice;
import com.java.entity.Query;

import net.sf.json.JSONObject;

public interface NoticeService {
	
	public JSONObject managerList(HttpServletRequest request, Query query) throws Exception;
	
	public JSONObject editList(HttpServletRequest request);
	
	public OaNotice edit(HttpServletRequest request);
	
	public JSONObject save(OaNotice notice,HttpServletRequest request);
	
	public JSONObject delete(HttpServletRequest request);
	
	
}
