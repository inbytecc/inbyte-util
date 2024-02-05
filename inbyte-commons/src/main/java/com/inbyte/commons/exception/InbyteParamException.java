package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 参数校验异常
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class InbyteParamException extends RuntimeException {

	private R r;

	public InbyteParamException() {
		super();
	}

	public InbyteParamException(String msg) {
		super(msg);
	}
	public InbyteParamException(R r) {
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static InbyteParamException failure(String msg) {
		return new InbyteParamException(R.failure(msg));
	}

}
