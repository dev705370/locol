package com.locol.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.openejb.util.Strings;

import com.locol.exceptions.BusinessException;

public class ValidationHelper {
	private List<String> errorList = new ArrayList<>();

	public void errorIfNullorEmpty(String validationString, String error) {
		if (Strings.checkNullBlankString(validationString)) {
			errorList.add(error);
		}
	}

	public List<String> getErrors() {
		return errorList;
	}

	public void logIfErrors() {
		if (!errorList.isEmpty()) {
			// TODO change to log
			System.out.println(errorList);
		}
	}

	public void throwAllErrors() throws BusinessException {
		if (!errorList.isEmpty()) {
			throw new BusinessException(errorList);
		}
	}

}
