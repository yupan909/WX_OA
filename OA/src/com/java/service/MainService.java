package com.java.service;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.java.entity.OaMain;

public interface MainService {
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	public OaMain select(String id);
	
	/**
	 * 提交流程
	 */
	public JSONObject submitFLow(HttpServletRequest request);
	
	/**
	 * 办结
	 */
	public JSONObject endFLow(HttpServletRequest request);
	
	/**
	 * 审批记录
	 */
	public JSONObject getFLowLog(HttpServletRequest request);
}
