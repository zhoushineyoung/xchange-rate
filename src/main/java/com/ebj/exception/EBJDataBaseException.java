package com.ebj.exception;

import java.sql.SQLException;

public class EBJDataBaseException extends RuntimeException {
	private static final long serialVersionUID = 1431511749419561961L;
	
	private SQLException e;

	public EBJDataBaseException(String msg) {
		this(msg, null);
	}

	public EBJDataBaseException(String msg, SQLException e) {
		super(msg, e);
		this.e = e;
	}

	public EBJDataBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public int getErrorCode() {
		if (e != null)
			return e.getErrorCode();
		else
			return -1;
	}
}
