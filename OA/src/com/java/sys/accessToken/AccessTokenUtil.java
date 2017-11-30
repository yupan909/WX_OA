package com.java.sys.accessToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.UUID;
import com.java.common.util.JStringUtil;
import com.java.common.util.URLUtil;
import com.java.sys.WXConstant;
import com.java.sys.jdbc.ConnectionManager;

import net.sf.json.JSONObject;

/**
 * 获取AccessToken（AccessToken是企业号的全局唯一票据，调用接口时需携带AccessToken）
 * @author Administrator
 *
 */
public class AccessTokenUtil {
	
	/**
	 * 获取AccessToken
	 * @param appID  微信公众号凭证
	 * @param appScret 微信公众号凭证秘钥
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = null;
		// 访问微信服务器请求获取AccessToken
		String url = WXConstant.URL_GETTOKEN.replace("CORPID", WXConstant.CORPID).replace("CORPSECRET", WXConstant.CORPSECRET);
		JSONObject json = URLUtil.sendGet(url, "");
		//获取accessToken
		String accessToken = json.getString("access_token");
		int expiresIn = new Integer(json.getString("expires_in"));
		//保存到数据库操作
		boolean bool = false;
		if(JStringUtil.isEmpty(AccessTokenUtil.selectAccessToken())){ //判断数据库是否存在，无则新增，有则修改
			bool = AccessTokenUtil.saveAccessToken(accessToken); //新增操作
		}else{
			bool = AccessTokenUtil.updateAccessToken(accessToken); //更新操作
		}
		if(bool){ //返回对象
			token = new AccessToken(accessToken,expiresIn);
		}
		return token;
	}
	
	/**
	 * 数据中查询AccessToken
	 * @param corpid
	 * @param corpsecret
	 * @return
	 */
	public static String selectAccessToken(){
		String sql = "select access_token from ACCESS_TOKEN where CORPID = ? and corpsecret = ?";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, WXConstant.CORPID);
			stmt.setString(2, WXConstant.CORPSECRET);
			rs = stmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					return rs.getString(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.close(conn, stmt, rs);
		}
		return null;
	}
	
	/**
	 * 保存AccessToken到数据库中
	 * @param accessToken 
	 * @return
	 */
	public static boolean saveAccessToken(String accessToken){
		String sql = "insert into ACCESS_TOKEN values(?, ?, ?, ?, ?, ?)";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			String nowDate = JStringUtil.getNowDate();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, UUID.randomUUID().toString().replace("-", "")); //ID
			stmt.setString(2, nowDate); //创建时间
			stmt.setString(3, WXConstant.CORPID); //微信公众号凭证
			stmt.setString(4, WXConstant.CORPSECRET); //微信公众号凭证秘钥
			stmt.setString(5, accessToken); //对应的AccessToken
			stmt.setString(6, nowDate); //更新时间
			int i = stmt.executeUpdate();
			if(i>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.close(conn, stmt, null);
		}
		return false;
	}
	
	/**
	 * 更新AccessToken到数据库中
	 * @param accessToken 
	 * @return
	 */
	public static boolean updateAccessToken(String accessToken){
		String sql = "update ACCESS_TOKEN set access_token = ?, up_time = ? where corpid = ? and corpsecret = ?";
		Connection conn = ConnectionManager.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, accessToken); //对应的AccessToken
			stmt.setString(2, JStringUtil.getNowDate()); //更新时间
			stmt.setString(3, WXConstant.CORPID); //微信公众号凭证
			stmt.setString(4, WXConstant.CORPSECRET); //微信公众号凭证秘钥
			int i = stmt.executeUpdate();
			if(i>0){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionManager.close(conn, stmt, null);
		}
		return false;
	}
	
}
