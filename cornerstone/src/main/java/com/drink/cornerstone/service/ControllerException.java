package com.drink.cornerstone.service;

public class ControllerException extends RuntimeException {

	private static final long serialVersionUID = 9104391884669940040L;

	public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }


}