package com.java.sys.accessToken;
/**
 * AccessToken是企业号的全局唯一票据，调用接口时需携带AccessToken
 * @author Administrator
 */
public class AccessToken {
	private String accessToken; 
	private int expiresIn; //有效时间
	
	public AccessToken() {
		super();
	}

	public AccessToken(String accessToken, int expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "AccessToken [accessToken=" + accessToken + ", expiresIn="
				+ expiresIn + "]";
	}
	
}
