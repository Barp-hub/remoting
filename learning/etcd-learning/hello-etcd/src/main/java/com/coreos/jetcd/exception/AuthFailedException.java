package com.coreos.jetcd.exception;

public class AuthFailedException extends Exception {

	public AuthFailedException(String cause, Throwable throwable) {
		super(cause, throwable);
	}
}
