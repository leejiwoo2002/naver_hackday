package com.hackday.sns_timeline.error.customException;

public class RepositoryNullException extends RuntimeException {
	public RepositoryNullException() {
		super();
	}
	public RepositoryNullException(String message, Throwable cause) {
		super(message, cause);
	}
	public RepositoryNullException(String message) {
		super(message);
	}
	public RepositoryNullException(Throwable cause) {
		super(cause);
	}
}
