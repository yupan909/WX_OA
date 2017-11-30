package com.java.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共常量类
 * @author Administrator
 *
 */
public class Constant {
	
	
	/*
	 * 分页中每页显示的记录数
	 */
	public static final int PAGE_NUM = 10;
	
	/*
	 * 状态编码 
	 */
	public static final String SUBFLAG_CG = "00";  //草稿
	public static final String SUBFLAG_BLZ = "01"; //办理中
	public static final String SUBFLAG_BJ = "02";  //已办结
	
	
	/*
	 * 业务类型编码 
	 */
	public static final String TYPE_APPROVE_BUY = "001001";  //审批管理 --采购申请
	public static final String TYPE_APPROVE_FINANCE = "001002"; //审批管理 --费用报销申请
	
	public static final String TYPE_VACATION = "002001"; //假期管理
	
	public static final String TYPE_REPORT = "003001"; //工作报告
	
	
	/*
	 * 业务类型标题
	 */
	public static final String TITLE_APPROVE_BUY = "采购申请单";
	public static final String TITLE_APPROVE_FINANCE = "费用报销申请单";
	
	public static final String TITLE_VACATION = "请假单";
	
	/*
	 * 页面起草页名称
	 */
	public static final String VIEW_APPROVE_BUY = "/views/approve/approve_buy_form.html"; 
	public static final String VIEW_APPROVE_FINANCE = "/views/approve/approve_finance_form.html"; 
	
	public static final String VIEW_VACATION = "/views/vacation/vacation_form.html";
	
	public static final String VIEW_REPORT = "/views/report/report_form.html"; 
	
	
	/*
	 * 假期种类 
	 */
	public static final Map<String,String> VACATION_MAP = new HashMap<String, String>();
	static{
		VACATION_MAP.put("01", "出差");
		VACATION_MAP.put("02", "事假");
		VACATION_MAP.put("03", "病假");
		VACATION_MAP.put("04", "产假");
		VACATION_MAP.put("05", "年假");
		VACATION_MAP.put("06", "婚假");
		VACATION_MAP.put("07", "丧假");
	}
	
}
