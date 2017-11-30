package com.java.service;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.java.entity.OaVacation;
import com.java.entity.Query;

public interface VacationService {
	
	public JSONObject managerList(HttpServletRequest request, Query query);
	
	public JSONObject editList(HttpServletRequest request);
	
	public OaVacation edit(HttpServletRequest request);
	
	public JSONObject save(OaVacation vacation,HttpServletRequest request);
	
	public JSONObject delete(HttpServletRequest request);
	
	
}
