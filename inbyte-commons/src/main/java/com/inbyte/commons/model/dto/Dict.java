package com.inbyte.commons.model.dto;

/**
 * 字典
 *
 * @author chenjw
 */
public class Dict {

    /**
     * 编码 Integer
     */
    private Integer code;
    /**
     * 编码 String
     */
    private String codeStr;
    /**
     * 名称
     */
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dict() {
    }

    public Dict(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Dict(Integer code, String codeStr, String name) {
        this.code = code;
        this.codeStr = codeStr;
        this.name = name;
    }
}
