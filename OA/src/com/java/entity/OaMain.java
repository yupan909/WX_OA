package com.java.entity;

public class OaMain {
	private String id;
	private String type; //业务类型
	private String creTime; //创建时间
	private String creUserId; //创建人id
	private String creUserName; //创建人名称
	private String creDeptId; //部门id
	private String creDeptName; //部门名称
	private String title; //标题
	private String subflag; //状态
	private String endTime; //完成时间
	
	public OaMain() {
		super();
	}

	public OaMain(String id, String type, String creTime, String creUserId,
			String creUserName, String title, String subflag, String endTime) {
		super();
		this.id = id;
		this.type = type;
		this.creTime = creTime;
		this.creUserId = creUserId;
		this.creUserName = creUserName;
		this.title = title;
		this.subflag = subflag;
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreTime() {
		return creTime;
	}

	public void setCreTime(String creTime) {
		this.creTime = creTime;
	}

	public String getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(String creUserId) {
		this.creUserId = creUserId;
	}

	public String getCreUserName() {
		return creUserName;
	}

	public void setCreUserName(String creUserName) {
		this.creUserName = creUserName;
	}

	public String getCreDeptId() {
		return creDeptId;
	}

	public void setCreDeptId(String creDeptId) {
		this.creDeptId = creDeptId;
	}

	public String getCreDeptName() {
		return creDeptName;
	}

	public void setCreDeptName(String creDeptName) {
		this.creDeptName = creDeptName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
