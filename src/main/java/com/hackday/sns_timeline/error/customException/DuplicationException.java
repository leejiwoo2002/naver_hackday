package com.hackday.sns_timeline.error.customException;

public class DuplicationException extends RuntimeException {

	public DuplicationException() {
		super();
	}
	public DuplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	public DuplicationException(String message) {
		super(message);
	}
	public DuplicationException(Throwable cause) {
		super(cause);
	}
}
