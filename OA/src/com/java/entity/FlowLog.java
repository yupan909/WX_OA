package com.java.entity;

public class FlowLog {
	private String id;
	private String recordId;
	private String sendTime;
	private String sendUserId;
	private String sendUserName;
	private String receiveUserId;
	private String receiveUserName;
	private String idea;
	private String isBack;
	
	public FlowLog() {
		super();
	}

	public FlowLog(String id, String recordId, String sendTime,
			String sendUserId, String sendUserName, String receiveUserId,
			String receiveUserName, String idea, String isBack) {
		super();
		this.id = id;
		this.recordId = recordId;
		this.sendTime = sendTime;
		this.sendUserId = sendUserId;
		this.sendUserName = sendUserName;
		this.receiveUserId = receiveUserId;
		this.receiveUserName = receiveUserName;
		this.idea = idea;
		this.isBack = isBack;
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

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}

	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
}
