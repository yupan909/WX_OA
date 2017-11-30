package com.java.entity;

public class FlowWrite {
	private String id;
	private String recordId;
	private String logId;
	private String createTime;
	private String userId;
	private String userName;
	private String preUserId;
	private String preUserName;
	
	public FlowWrite() {
		super();
	}

	public FlowWrite(String id, String recordId, String logId,
			String createTime, String userId, String userName,
			String preUserId, String preUserName) {
		super();
		this.id = id;
		this.recordId = recordId;
		this.logId = logId;
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName;
		this.preUserId = preUserId;
		this.preUserName = preUserName;
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

	public String getPreUserId() {
		return preUserId;
	}

	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}

	public String getPreUserName() {
		return preUserName;
	}

	public void setPreUserName(String preUserName) {
		this.preUserName = preUserName;
	}
	
}
