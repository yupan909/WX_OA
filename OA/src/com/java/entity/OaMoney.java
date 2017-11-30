package com.java.entity;

public class OaMoney {
	private String id;
	private String createTime;
	private String moneyTitle;
	private String moneyMonth;
	
	public OaMoney() {
		super();
	}

	public OaMoney(String id, String createTime, String moneyTitle,
			String moneyMonth) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.moneyTitle = moneyTitle;
		this.moneyMonth = moneyMonth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMoneyTitle() {
		return moneyTitle;
	}

	public void setMoneyTitle(String moneyTitle) {
		this.moneyTitle = moneyTitle;
	}

	public String getMoneyMonth() {
		return moneyMonth;
	}

	public void setMoneyMonth(String moneyMonth) {
		this.moneyMonth = moneyMonth;
	}
	
}
