package com.flysall.util;

public class Response {
	private int state;
	private String message;
	private Object data;

	public Response() {

	}

	public Response(int state) {
		this.state = state;
	}

	public Response(int state, String message) {
		this.state = state;
		this.message = message;
	}

	public Response(int state, String message, Object data) {
		this.state = state;
		this.message = message;
		this.data = data;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
