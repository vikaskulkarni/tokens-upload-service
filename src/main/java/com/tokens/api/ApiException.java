package com.tokens.api;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-18T15:17:56.845-07:00")

public class ApiException extends Exception {
	private static final long serialVersionUID = 5265177832668512670L;
	private int code;

	public ApiException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
