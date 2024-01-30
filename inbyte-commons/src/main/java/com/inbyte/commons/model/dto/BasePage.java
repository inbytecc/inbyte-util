package com.inbyte.commons.model.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 分页参数基类
 *
 * @author chenjw
 */
public class BasePage {

    /**
     * 当前页序号
     */
    @NotNull(message = "分页查询参数不能为空")
    @Min(value = 0, message = "当前页参数不正确")
    @Max(value = 5000, message = "最大查询 5000 页内数据")
    private Integer pageNum;
    /**
     * 每页查询行数
     */
    @NotNull(message = "分页查询参数不能为空")
    @Min(value = 1, message = "单页最少查询 1 条数据")
    @Max(value = 200, message = "单页最大查询 200 条数据")
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
