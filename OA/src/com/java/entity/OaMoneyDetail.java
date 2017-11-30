package com.java.entity;

public class OaMoneyDetail {
	private String id;
	private String recordId;
	private String empId;
	private String empName;
	private String empDeptName;
	private String moneyBase;
	private String moneyJx;
	private String moneyJj;
	private String moneyCb;
	private String moneyBx;
	private String moneyAll;
	
	public OaMoneyDetail() {
		super();
	}
	
	public OaMoneyDetail(String id, String recordId, String empId,
			String empName, String moneyBase, String moneyJx, String moneyJj,
			String moneyCb, String moneyBx, String moneyAll) {
		super();
		this.id = id;
		this.recordId = recordId;
		this.empId = empId;
		this.empName = empName;
		this.moneyBase = moneyBase;
		this.moneyJx = moneyJx;
		this.moneyJj = moneyJj;
		this.moneyCb = moneyCb;
		this.moneyBx = moneyBx;
		this.moneyAll = moneyAll;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getEmpDeptName() {
		return empDeptName;
	}

	public void setEmpDeptName(String empDeptName) {
		this.empDeptName = empDeptName;
	}

	public String getMoneyBase() {
		return moneyBase;
	}

	public void setMoneyBase(String moneyBase) {
		this.moneyBase = moneyBase;
	}

	public String getMoneyJx() {
		return moneyJx;
	}

	public void setMoneyJx(String moneyJx) {
		this.moneyJx = moneyJx;
	}

	public String getMoneyJj() {
		return moneyJj;
	}

	public void setMoneyJj(String moneyJj) {
		this.moneyJj = moneyJj;
	}

	public String getMoneyCb() {
		return moneyCb;
	}

	public void setMoneyCb(String moneyCb) {
		this.moneyCb = moneyCb;
	}

	public String getMoneyBx() {
		return moneyBx;
	}

	public void setMoneyBx(String moneyBx) {
		this.moneyBx = moneyBx;
	}

	public String getMoneyAll() {
		return moneyAll;
	}

	public void setMoneyAll(String moneyAll) {
		this.moneyAll = moneyAll;
	}

}
