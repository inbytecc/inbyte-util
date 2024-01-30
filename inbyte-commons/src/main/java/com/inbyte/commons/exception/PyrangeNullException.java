package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 参数为Null错误
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class PyrangeNullException extends RuntimeException {

	private R r;

	public PyrangeNullException() {
		super();
	}

	public PyrangeNullException(String msg) {
		super(msg);
	}
	public PyrangeNullException(R r) {
		super(r.getMsg());
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static PyrangeNullException msg(String msg) {
		return new PyrangeNullException(R.error(msg));
	}

}
