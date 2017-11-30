package com.java.entity;
/**
 * 查询条件实体类
 * @author Administrator
 *
 */
public class Query {
	private String startTime; //开始时间
	private String endTime; //结束时间
	private String dept; //部门
	private String user; //员工
	private String type; //类型
	private String title; //标题
	private String subflag; //状态
	private String month; //月份
	private String recordId; //关联Id
	
	private int startIndex; //分页开始索引
	private int endIndex; //分页结束索引
	
	public Query() {
		super();
	}

	public Query(String startTime, String endTime, String dept, String user,
			String type, String title, String subflag) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.dept = dept;
		this.user = user;
		this.type = type;
		this.title = title;
		this.subflag = subflag;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
}
