package com.inbyte.commons.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页参数基类
 *
 * @author chenjw
 */
public class BaseQuery extends BasePage{

    /**
     * 当前场馆ID
     * 服务端参数，前端忽略
     * @ignore
     */
    @JsonIgnore
    private Integer venueId;
    /**
     * 当前场馆ID
     * 服务端参数，前端忽略
     * @ignore
     */
    @JsonIgnore
    private String mctNo;

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }
}
