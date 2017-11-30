package com.java.sys.accessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * 项目启动的时候启动该servlet, servlet初始化时启动该线程
 * @author Administrator
 *
 */
public class AccessTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 初始化启动
	 */
	public void init() throws ServletException {
		//启动定时获取access_token的线程
		new Thread(new AccessTokenThread()).start();
	}

}
