package com.inbyte.commons.model.dict;

/**
 * 是否
 * 杭州湃橙体育科技有限公司
 * @author chenjw
 * @date 2016年08月29日
 */
public enum WhetherDict {

    No(0, "否"),
    Yes(1, "是");

    public final int code;
    public final String name;

    WhetherDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public boolean yes() {
        return this == Yes ? true : false;
    }
}
