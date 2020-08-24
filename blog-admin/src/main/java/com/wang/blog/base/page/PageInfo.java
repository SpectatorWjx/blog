package com.wang.blog.base.page;

import lombok.Data;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
@Data
public class PageInfo {

    private int number;

    private int size;

    private long total;

    private int totalPages;

    public PageInfo(int number, int size, long total, int totalPages){
        this.number = number;
        this.size = size;
        this.total = total;
        this.totalPages = totalPages;
    }
}
