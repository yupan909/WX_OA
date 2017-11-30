package com.java.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.common.Result;
import com.java.common.util.JStringUtil;
import com.java.common.util.URLUtil;
import com.java.entity.OaApproveBuy;
import com.java.entity.User;
import com.java.service.ApproveService;
import com.java.sys.WXConstant;
import com.java.sys.accessToken.AccessTokenUtil;

@Controller
public class LoginController {
		
	/**
	 * 登录时身份验证，企业获取code
	 */
	@RequestMapping("/login")
	public ModelAndView login(){
		String redirectUrl = WXConstant.URL_AUTHORIZE;
		redirectUrl = redirectUrl.replace("CORPID", WXConstant.CORPID).replace("REDIRECT_URI", WXConstant.IP + "/dologin.do");
		ModelAndView modelAndView = new ModelAndView("redirect:"+redirectUrl);
		return modelAndView;
	}
	
	/**
	 * 根据code获取登录成员的成员ID，且保存在session中
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dologin")
	public ModelAndView dologin(HttpServletRequest request, HttpServletResponse response, @RequestParam String code){
		ModelAndView modelAndView = new ModelAndView();
		
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		
		if(JStringUtil.isEmpty(accessToken) || JStringUtil.isEmpty(code)){
			modelAndView.setViewName("redirect:/error.html"); //进入错误提示页
		}else{
			//获取企业号登录的成员信息的请求路径
			String getUserInfoUrl = WXConstant.URL_GETUSERINFO; 
			getUserInfoUrl = getUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("CODE", code);
			//发送请求
			JSONObject json = URLUtil.sendGet(getUserInfoUrl, "");
			if(null!=json){ //获取成员信息成功时
				String userId = json.getString("UserId"); //成员UserID
				
				//将用户id保存在session中
				if(savaUserToSession(request, accessToken, userId)){
					modelAndView.setViewName("redirect:/index.html"); //进入首页
				}else{
					modelAndView.setViewName("redirect:/error.html"); //进入错误提示页
				}
				
			}else{ //失败
				modelAndView.setViewName("redirect:/error.html"); //进入错误提示页
			}
		}
		return modelAndView;
	}
	
	/**
	 * 将用户信息保存在session中
	 * @param request
	 * @param userId
	 */
	public boolean savaUserToSession(HttpServletRequest request, String accessToken, String userId){
		HttpSession session = request.getSession();
		//获取成员请求
		String getUserUrl = WXConstant.URL_GET; 
		getUserUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("USERID", userId);
		//发送get请求
		JSONObject json = URLUtil.sendGet(getUserUrl, "");
		if(null!=json){
			User user = new User();
			user.setUserId(userId);
			user.setUserName(json.getString("name"));
			
			//获取部门ID和名称
			Integer deptId = null;
			JSONArray department = (JSONArray)json.get("department"); //部门id
			if(department.size()==1){
				deptId =  (Integer)department.get(0);
			}else{
				for(int i=0; i<department.size(); i++){
					Integer s = (Integer)department.get(i);
					if(1!=s){
						deptId = s;
					}
				}
			}
			//获取部门列表
			Map<String,String> deptMap = new HashMap<String,String>();
			String getDeptUserUrl = WXConstant.URL_GETDEPTLIST; 
			getDeptUserUrl = getDeptUserUrl.replace("ACCESS_TOKEN", accessToken).replace("ID", "1");
			JSONObject deptJson = URLUtil.sendGet(getDeptUserUrl, "");
			JSONArray deptArray = (JSONArray)deptJson.get("department");
			for(int i=0; i<deptArray.size(); i++){
				JSONObject obj = (JSONObject)deptArray.get(i);
				deptMap.put(obj.getString("id"), obj.getString("name"));
			}
			
			user.setDeptId(deptId.toString());
			user.setDeptName(deptMap.get(deptId.toString()));
			session.setAttribute("user", user);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 授权登录后台管理中心
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginManager")
	public ModelAndView loginManager(HttpServletRequest request) throws Exception{
		//微信登录授权页面
		String loginPage = WXConstant.URL_LOGINPAGE;
		loginPage = loginPage.replace("CORPID", WXConstant.CORPID).replace("REDIRECT_URI", WXConstant.IP + "/dologinManager.do").replace("USERTYPE", "admin");
		ModelAndView modelAndView = new ModelAndView("redirect:"+loginPage);
		return modelAndView;
	}
	
	/**
	 * 授权登录之后目的跳转网址
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dologinManager")
	public ModelAndView dologinManager(HttpServletRequest request, @RequestParam String auth_code) throws Exception{
		HttpSession session = request.getSession();
		ModelAndView modelAndView = new ModelAndView();
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		//获取企业号登录用户信息
		String getLoginInfoUrl = WXConstant.URL_GETLOGININFO;
		getLoginInfoUrl = getLoginInfoUrl.replace("ACCESS_TOKEN", accessToken);
		//请求JSON格式的参数
		JSONObject param = new JSONObject();
		param.put("auth_code", auth_code);
		//发送get请求
		JSONObject json = URLUtil.sendPost(getLoginInfoUrl, param.toString());
		//获取用户信息，且保存在session当中
		if(json.containsKey("user_info")){
			JSONObject userInfo = (JSONObject)json.get("user_info"); //用户信息
			User user = new User();
			user.setUserId(userInfo.getString("userid"));
			user.setUserName(userInfo.getString("name"));
			session.setAttribute("user", user);
		}
		modelAndView.setViewName("redirect:/views/manager/manager_home.html"); //进入后台管理中心首页
		return modelAndView;
	}
	
	/**
	 * 密码登录后台管理中心
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginHome")
	@ResponseBody
	public String loginHome(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		JSONObject result = new JSONObject();
		String username = request.getParameter("username"); //用户名
		String password = request.getParameter("password"); //密码
		if("admin".equals(username) && "1".equals(password)){
			result.put("msg", "1");
			User user = new User();
			user.setUserId(username);
			user.setUserName("系统管理员");
			session.setAttribute("user", user);
		}else{
			result.put("msg", "0");
		}
		return result.toString();
	}
	
	/**
	 * 测试登录专用
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/testLogin")
	public ModelAndView testLogin(HttpServletRequest request) throws UnsupportedEncodingException{
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId"); //用户Id
		String userName = request.getParameter("userName"); //用户名称
		String deptId = request.getParameter("deptId"); //用户名称
		String deptName = request.getParameter("deptName"); //用户名称
		userName = URLDecoder.decode(userName,"UTF-8");
		deptName = URLDecoder.decode(deptName,"UTF-8");
		
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		user.setDeptId(deptId);
		user.setDeptName(deptName);
		session.setAttribute("user", user);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/index.html");
		return modelAndView;
	}
}
