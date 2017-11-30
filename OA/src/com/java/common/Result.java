package com.java.common;

/**
 * 返回结果信息类
 */
public class Result {

	private String id;
	private String msg;
	private Object obj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public Result(){};
	
	public Result(String msg, String id) {
		this.msg = msg;
		this.id = id;
	}
	
	public Result(String msg, String id, Object obj) {
		this.msg = msg;
		this.id = id;
		this.obj = obj;
	}
}
