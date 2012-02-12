package com.github.kolorobot.web.message;

public class Message {

	public enum MessageType {
		Info, Error;
	}

	private MessageType type;

	private String key;

	private Object[] args;

	public Message(String key, MessageType type, Object... args) {
		super();
		this.type = type;
		this.key = key;
		this.args = args;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public boolean isError() {
		return MessageType.Error.equals(getType());
	}
}
