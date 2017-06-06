package com.locol.exceptions;

import java.util.List;

public class BusinessException extends Exception {
	List<String> errors;

	public BusinessException(List<String> errorList) {
		super(new Throwable(errorList.toString()));
		errors = errorList;
	}

	public BusinessException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = -7925469744873434912L;

}
