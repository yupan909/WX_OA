package com.test;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.json.JSONObject;

import com.java.common.Constant;
import com.java.dao.OaApproveDao;
import com.java.sys.accessToken.AccessTokenUtil;


public class TestDao {
	private static OaApproveDao oaApproveDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext cxt=new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		oaApproveDao=  (OaApproveDao) cxt.getBean("oaApproveDao");
	}
	
	@Test
	public static void test(){
		oaApproveDao.selectListCG(null);
	}
	
}
