package com.augustczar.cursomc.exceptions;

public class FileRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileRuntimeException(String message) {
		super(message);
	}

}
