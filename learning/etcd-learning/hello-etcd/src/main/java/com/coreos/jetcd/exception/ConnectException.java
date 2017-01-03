package com.coreos.jetcd.exception;

public class ConnectException extends Exception {

	public ConnectException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public ConnectException() {
	}
}
