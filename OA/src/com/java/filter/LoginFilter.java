package com.java.filter;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java.entity.User;
/**
 * 登录过滤器
 * @author Administrator
 *
 */
public class LoginFilter implements HandlerInterceptor {

	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);
	
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		User user = (User)request.getSession().getAttribute("user"); //获取当前用户
		
		String url = request.getRequestURI();
		String[] pages = new String[]{"login.do","dologin.do","getDeptlist.do","getUserlist.do","testLogin.do","loginHome.do","loginManager.do","dologinManager.do"};
		String url_end = url.substring(url.lastIndexOf("/")+1,url.length());
		if(Arrays.asList(pages).contains(url_end)){
			LOGGER.info("用户登录，不用拦截");
			return true;
		}else{
			if(null!=user){
				 LOGGER.info("用户发送请求，进行拦截");
				 response.setHeader("sessionstatus","ok");//在响应头设置session状态
				return true;
			}else{
				 //如果是ajax请求响应头会有x-requested-with
				if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
					response.setHeader("sessionstatus","timeout");//在响应头设置session状态
				}else{
					response.sendRedirect("/login.jsp"); 
				}
				LOGGER.info("用户登录过期");
			}
		}
		return false;
	}

}
