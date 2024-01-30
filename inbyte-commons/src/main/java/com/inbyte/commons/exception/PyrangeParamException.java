package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 参数校验异常
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class PyrangeParamException extends RuntimeException {

	private R r;

	public PyrangeParamException() {
		super();
	}

	public PyrangeParamException(String msg) {
		super(msg);
	}
	public PyrangeParamException(R r) {
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static PyrangeParamException failure(String msg) {
		return new PyrangeParamException(R.failure(msg));
	}

}
