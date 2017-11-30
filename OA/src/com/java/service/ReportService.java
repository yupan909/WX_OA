package com.java.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.java.entity.OaReport;
import com.java.entity.Query;

public interface ReportService {
	
	public JSONObject managerList(HttpServletRequest request, Query query) throws Exception;
	
	public JSONObject editList(HttpServletRequest request);
	
	public OaReport edit(HttpServletRequest request);
	
	public JSONObject save(OaReport report,HttpServletRequest request);
	
	public JSONObject delete(HttpServletRequest request);
	
	
}
