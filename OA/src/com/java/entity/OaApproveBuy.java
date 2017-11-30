package com.java.entity;

public class OaApproveBuy {
	private String id;
	private String buyName; //采购物品
	private String buyNumber; //采购数量
	private String buyStandard; //规格及型号
	private String buyPrice; //单价
	private String buyReason; //采购事由
	private String buyDept; //使用部门
	
	public OaApproveBuy() {
		super();
	}

	public OaApproveBuy(String id, String buyName, String buyNumber,
			String buyStandard, String buyPrice, String buyReason,
			String buyDept) {
		super();
		this.id = id;
		this.buyName = buyName;
		this.buyNumber = buyNumber;
		this.buyStandard = buyStandard;
		this.buyPrice = buyPrice;
		this.buyReason = buyReason;
		this.buyDept = buyDept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public String getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(String buyNumber) {
		this.buyNumber = buyNumber;
	}

	public String getBuyStandard() {
		return buyStandard;
	}

	public void setBuyStandard(String buyStandard) {
		this.buyStandard = buyStandard;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getBuyReason() {
		return buyReason;
	}

	public void setBuyReason(String buyReason) {
		this.buyReason = buyReason;
	}

	public String getBuyDept() {
		return buyDept;
	}

	public void setBuyDept(String buyDept) {
		this.buyDept = buyDept;
	}
	
}
