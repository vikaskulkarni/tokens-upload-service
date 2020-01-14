package com.tokens.model;

public enum STATUS {

	STARTED("File Upload Started", false) {
		@Override
		String operationStatus() {
			return "STARTED";
		}
	},

	COMPLETED("File Upload Completed", true) {
		@Override
		String operationStatus() {
			return "COMPLETED";
		}
	};

	private boolean isCompleted;
	private String msg;

	abstract String operationStatus();

	STATUS(String msg, final boolean isCompleted) {
		this(msg);
		this.isCompleted = isCompleted;
	}

	STATUS(String msg) {
		this.msg = msg;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public String getMsg() {
		return msg;
	}

}
