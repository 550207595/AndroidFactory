package com.jeiel.wechatui;

public class Msg{
	private String content;
	private int type;
	public static final int SEND=0;
	public static final int RECEIVE=1;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}