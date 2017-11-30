package com.java.entity;

public class FlowRead {
	private String id;
	private String recordId;
	private String logId;
	private String createTime;
	private String userId;
	private String userName;
	
	public FlowRead() {
		super();
	}

	public FlowRead(String id, String recordId, String logId,
			String createTime, String userId, String userName) {
		super();
		this.id = id;
		this.recordId = recordId;
		this.logId = logId;
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName;
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

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
