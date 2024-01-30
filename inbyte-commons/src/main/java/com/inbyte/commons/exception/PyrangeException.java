package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 平台业务异常
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class PyrangeException extends RuntimeException {

	private R r;

	public PyrangeException() {
		super();
	}

	public PyrangeException(String msg) {
		super(msg);
	}
	public PyrangeException(R r) {
		super(r.getMsg());
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static PyrangeException error(String msg) {
		return new PyrangeException(R.error(msg));
	}

	public static PyrangeException error(R r) {
		return new PyrangeException(r);
	}

}
