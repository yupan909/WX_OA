package com.java.entity;

public class OaApproveFinance {
	private String id;
	private String financeType; //报销类目
	private String financeDate; //发生日期
	private String financeContent; //报销内容
	private String financeMoney; //报销金额
	
	public OaApproveFinance() {
		super();
	}

	public OaApproveFinance(String id, String financeType, String financeDate,
			String financeContent, String financeMoney) {
		super();
		this.id = id;
		this.financeType = financeType;
		this.financeDate = financeDate;
		this.financeContent = financeContent;
		this.financeMoney = financeMoney;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFinanceType() {
		return financeType;
	}

	public void setFinanceType(String financeType) {
		this.financeType = financeType;
	}

	public String getFinanceDate() {
		return financeDate;
	}

	public void setFinanceDate(String financeDate) {
		this.financeDate = financeDate;
	}

	public String getFinanceContent() {
		return financeContent;
	}

	public void setFinanceContent(String financeContent) {
		this.financeContent = financeContent;
	}

	public String getFinanceMoney() {
		return financeMoney;
	}

	public void setFinanceMoney(String financeMoney) {
		this.financeMoney = financeMoney;
	}

}
