package com.wang.common.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
@Data
public class PageInfo {

    @ApiModelProperty(value = "页码",name = "currentPage",dataType = "int")
    private int pageNumber;

//    @ApiModelProperty(value = "页面记录数",name = "pageSize",dataType = "int")
//    private int size;

    @ApiModelProperty(value = "总页数",name = "totalPage",dataType = "int")
    private long total;

    @ApiModelProperty(value = "总记录数",name = "totalCount",dataType = "long")
    private int totalPages;

    public PageInfo(int pageNumber, long total, int totalPages){
        this.pageNumber = pageNumber;
        this.total = total;
        this.totalPages = totalPages;
    }
}
