package com.java.sys.accessToken;

import com.java.common.util.JStringUtil;

/**
 * 定义AccessTokenThread线程，调用AccessTokenUtil获取accessToken
 * @author Administrator
 */
public class AccessTokenThread implements Runnable {
	public static AccessToken access_token=null;
	public void run() {
		while(true){
			try {
				//调用工具类获取access_token(每天请求次数为2000次，每次获取的有效期为7200秒)
				access_token = AccessTokenUtil.getAccessToken();
				if(null!=access_token){
					System.out.println("==============》》AccessToken获取成功："
				     +access_token.toString() + "("+JStringUtil.getNowDate()+")");
					Thread.sleep((access_token.getExpiresIn()-200)*1000); //7000秒之后重新进行获取
				}else{
					System.out.println("==============》》AccessToken获取失败....");
					
					Thread.sleep(60*1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
