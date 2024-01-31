package com.inbyte.commons.exception;

import com.inbyte.commons.model.dto.R;

/**
 * 参数为Null错误
 *
 * @author: chenjw
 * @date: 2020/8/14
 */
public class InbyteNullException extends RuntimeException {

	private R r;

	public InbyteNullException() {
		super();
	}

	public InbyteNullException(String msg) {
		super(msg);
	}
	public InbyteNullException(R r) {
		super(r.getMsg());
		this.r = r;
	}

	public R getResult() {
		return r;
	}

	public void setResult(R r) {
		this.r = r;
	}

	public static InbyteNullException msg(String msg) {
		return new InbyteNullException(R.error(msg));
	}

}
