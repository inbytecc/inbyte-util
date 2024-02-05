package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 平台业务异常
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class InbyteException extends RuntimeException {

	private R r;

	public InbyteException() {
		super();
	}

	public InbyteException(String msg) {
		super(msg);
	}
	public InbyteException(R r) {
		super(r.getMsg());
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static InbyteException error(String msg) {
		return new InbyteException(R.error(msg));
	}

	public static InbyteException error(R r) {
		return new InbyteException(r);
	}

}
