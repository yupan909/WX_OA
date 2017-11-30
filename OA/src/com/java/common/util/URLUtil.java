package com.java.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 请求工具类
 * @author Administrator
 *
 */
public class URLUtil {
	
	/**
	 * 向指定URL发送GET方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 返回资源的响应结果，其中是json类型
	 */
	public static JSONObject sendGet(String url, String param){
		JSONObject json = null;
		InputStream in = null;
        try {
            URL getUrl = new URL(url + param);
            // 打开和URL之间的连接
            HttpURLConnection http = (HttpURLConnection)getUrl.openConnection();
            // 设置请求属性
            http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// 设置超时时间  
			http.setConnectTimeout(10000);  
			http.setReadTimeout(10000);  
			// 发送POST请求必须设置如下两行 
			http.setDoOutput(true);
			http.setDoInput(true);
			// 建立实际的连接
			http.connect();
			
			//通过输入流来读取URL的响应
			in = http.getInputStream(); 
            StringBuilder message = new StringBuilder();
            int len = 0;
            int size = in.available(); 
            byte[] b = new byte[size];
            while((len=in.read(b))!=-1){
            	message.append(new String(b, "UTF-8"));
            }
            json = JSONObject.fromObject(message.toString());
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + "["+url+param+"]" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return json;
	}
	
	/**
	 * 向指定URL发送POST方法的请求
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 返回资源的响应结果，其中是json类型
	 */
	public static JSONObject sendPost(String url, String param){
		JSONObject json = null;
		OutputStreamWriter  out = null;
		InputStream in = null;
		try {
			URL getUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection http = (HttpURLConnection)getUrl.openConnection();
			// 设置请求属性
			http.setRequestMethod("POST"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// 设置超时时间  
			http.setConnectTimeout(10000);  
			http.setReadTimeout(10000);  
			// 发送POST请求必须设置如下两行 
			http.setDoOutput(true); 
			http.setDoInput(true);
			// 建立实际的连接
			http.connect();
			
			// 获取HttpURLConnection对象对应的输出流
            out = new OutputStreamWriter(http.getOutputStream(),"UTF-8");
            // 发送请求参数
            out.append(param);
            // flush输出流的缓冲
            out.flush();
			
            //通过输入流来读取URL的响应
			in = http.getInputStream(); 
            StringBuilder message = new StringBuilder();
            int len = 0;
            int size = in.available(); 
            byte[] b = new byte[size];
            while((len=in.read(b))!=-1){
            	message.append(new String(b, "UTF-8"));
            }
            json = JSONObject.fromObject(message.toString());
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + "["+url+"]" + e);
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
                    out.close();
                }
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return json;
	}
}
