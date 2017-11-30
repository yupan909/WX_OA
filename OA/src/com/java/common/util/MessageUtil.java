package com.java.common.util;

import com.java.sys.WXConstant;
import com.java.sys.accessToken.AccessTokenUtil;

import net.sf.json.JSONObject;

/**
 * 微信调用发消息接口
 * @author Administrator
 *
 */
public class MessageUtil {

	/**
	 * 发送提醒消息
	 * @param toUser 多个接收者用‘|’分隔
	 * @param content 消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
	 * @return
	 */
	public static JSONObject send(String toUser, String content){
		//参数ACCESS_TOKEN
		String accessToken = AccessTokenUtil.selectAccessToken();
		//发送消息接口
		String sendUrl = WXConstant.URL_SEND.replace("ACCESS_TOKEN", accessToken);
		
		//请求JSON格式的参数
		JSONObject param = new JSONObject();
		param.put("touser", toUser); //成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向关注该企业应用的全部成员发送
		param.put("msgtype", "text"); //消息类型，此时固定为：text （支持消息型应用跟主页型应用）
		param.put("agentid", "1"); //企业应用的id，整型。可在应用的设置页面查看
		//消息内容，最长不超过2048个字节，注意：主页型应用推送的文本消息在微信端最多只显示20个字（包含中英文）
		JSONObject textJson = new JSONObject();
		textJson.put("content", content); 
		param.put("text", textJson.toString());
		JSONObject returnJson = URLUtil.sendPost(sendUrl, param.toString());
		return returnJson;
	}
	
}
