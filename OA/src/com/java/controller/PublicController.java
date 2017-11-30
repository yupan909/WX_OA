package com.java.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.common.Constant;
import com.java.common.util.JStringUtil;
import com.java.common.util.URLUtil;
import com.java.entity.OaMain;
import com.java.entity.Query;
import com.java.entity.User;
import com.java.service.MainService;
import com.java.sys.WXConstant;
import com.java.sys.accessToken.AccessTokenUtil;

@Controller
@RequestMapping("/public")
public class PublicController {
	
	@Resource
	private MainService mainService;
		
	/**
	 * 获取当前系统时间和登录人姓名
	 * @param request
	 * @return
	 */
	@RequestMapping("/getLoginInfo")
	@ResponseBody
	public String getLoginInfo(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		
		JSONObject result = new JSONObject();
		result.put("date", JStringUtil.getNowDate2()); //当前时间
		result.put("name", user.getUserName()); //当前登录人名称
		return result.toString();
	}
	
	/**
	 * 页面调转
	 */
	@RequestMapping("/viewJump")
	public ModelAndView viewJump(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		
		String id = request.getParameter("id");
		String wid = request.getParameter("wid");
		String view = request.getParameter("view");
		
		String url = "/error.html"; //默认跳转错误页面
		if(!JStringUtil.isEmpty(id)){
			OaMain main = mainService.select(id);
			if(null!=main){
				//采购申请单
				if(Constant.TYPE_APPROVE_BUY.equals(main.getType())){
					url = Constant.VIEW_APPROVE_BUY;
				//费用报销申请单
				}else if(Constant.TYPE_APPROVE_FINANCE.equals(main.getType())){
					url = Constant.VIEW_APPROVE_FINANCE;
				//假期管理
				}else if(Constant.TYPE_VACATION.equals(main.getType())){
					url = Constant.VIEW_VACATION;
				//工作报告
				}else if(Constant.TYPE_REPORT.equals(main.getType())){
					url = Constant.VIEW_REPORT;
				}
				
				//指定跳转到查看页面
				if(view.equals("y")){ 
					url = url.replace("_form", "_view");  //跳转查看页面
				//流程中,且当前人不是起草人
				}else if(Constant.SUBFLAG_BLZ.equals(main.getSubflag()) && !userId.equals(main.getCreUserId())){ 
					url = url.replace("_form", "_edit");  //跳转编辑页面
				//流程已经办结完成时
				}else if(Constant.SUBFLAG_BJ.equals(main.getSubflag())){
					url = url.replace("_form", "_view");  //跳转查看页面
				}
				
				url += "?id="+id;
				if(!JStringUtil.isEmpty(wid)){
					url += "&wid="+wid;
				}
			}
		}
		ModelAndView modelAndView = new ModelAndView("redirect:"+url);
		return modelAndView;
	}
	
	/**
	 * 查询条件存入session中
	 */
	@RequestMapping("/managerSaveSession")
	@ResponseBody
	public String saveSession(HttpServletRequest request, Query query) throws Exception{
		HttpSession session = request.getSession();
		//存入session中
		session.setAttribute("query", query);
		JSONObject json = new JSONObject();
		json.put("msg", "1");
		return json.toString();
	}
	
	/**
	 * 获取审批记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFLowLog")
	@ResponseBody
	public String getFLowLog(HttpServletRequest request){
		JSONObject json = mainService.getFLowLog(request);
		return json.toString();
	}
	
	/**
	 * 获取当前企业部门列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeptlist")
	@ResponseBody
	public String getDeptlist(HttpServletRequest request){
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
				
		String getDeptUserUrl = WXConstant.URL_GETDEPTLIST; 
		getDeptUserUrl = getDeptUserUrl.replace("ACCESS_TOKEN", accessToken).replace("ID", "1");
		//发送get请求
		JSONObject json = URLUtil.sendGet(getDeptUserUrl, "");
		return json.toString();
	}
	
	/**
	 * 获取指定企业部门下用户列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserlist")
	@ResponseBody
	public String getUserlist(HttpServletRequest request){
		String dpetId = request.getParameter("deptId");
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		
		String getUserUrl = WXConstant.URL_GETDEPTUSERMORE; 
		getUserUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("DEPARTMENT_ID", dpetId).replace("FETCH_CHILD", "0").replace("STATUS", "0");
		//发送get请求
		JSONObject json = URLUtil.sendGet(getUserUrl, "");
		return json.toString();
	}
	
	/**
	 * 获取指定用户的详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(HttpServletRequest request){
		String userId = request.getParameter("userId"); //指定用户ID
		
		if(JStringUtil.isEmpty(userId)){ 
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user"); //获取当前用户
			userId = user.getUserId(); //获取当前用户ID
		}
		
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		
		//获取成员请求
		String getUserUrl = WXConstant.URL_GET; 
		getUserUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("USERID", userId);
		//发送get请求
		JSONObject json = URLUtil.sendGet(getUserUrl, "");
		
		return json.toString();
	}
	
	/**
	 * 更新用户的详细信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request){
		String userId = request.getParameter("userId"); //指定用户ID
		String gender = request.getParameter("gender"); //性别
		String position = request.getParameter("position"); //职位
		String weixinid = request.getParameter("weixinid"); //微信号
		String mobile = request.getParameter("mobile"); //手机
		String email = request.getParameter("email"); //邮箱
		
		if(JStringUtil.isEmpty(userId)){ 
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user"); //获取当前用户
			userId = user.getUserId(); //获取当前用户ID
		}
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		
		//更新成员请求
		String updateUrl = WXConstant.URL_UPDATE; 
		updateUrl = updateUrl.replace("ACCESS_TOKEN", accessToken);
		
		//请求JSON格式的参数
		JSONObject param = new JSONObject();
		param.put("userid", userId);
		param.put("gender", gender);
		param.put("position", position);
		param.put("weixinid", weixinid);
		param.put("mobile", mobile);
		param.put("email", email);
		
		//发送get请求
		JSONObject json = URLUtil.sendPost(updateUrl, param.toString());
		
		return json.toString();
	}
	
}
