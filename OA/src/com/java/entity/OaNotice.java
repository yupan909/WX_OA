package com.java.entity;

public class OaNotice {
	private String id;
	private String noticeTime;
	private String noticeTitle;
	private String noticeContent;
	
	public OaNotice() {
		super();
	}

	public OaNotice(String id, String noticeTime, String noticeTitle,
			String noticeContent) {
		super();
		this.id = id;
		this.noticeTime = noticeTime;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	
}
