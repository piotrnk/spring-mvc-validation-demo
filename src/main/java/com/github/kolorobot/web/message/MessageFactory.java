package com.github.kolorobot.web.message;

import com.github.kolorobot.web.message.Message.MessageType;

public class MessageFactory {

	public static Message createInfoMessage(String message) {
		return new Message(message, MessageType.Info);
	}

	public static Message createInfoMessage(String message, Object... args) {
		Message m = new Message(message, MessageType.Info, args);
		return m;
	}

	public static Message createErrorMessage(String error) {
		return new Message(error, MessageType.Error);
	}

	public static Message createErrorMessage(String error, Object... args) {
		return new Message(error, MessageType.Error, args);
	}
}
