package com.wang.blog.vo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wjx
 * @date 2019/12/10
 */
@Data
public class PermissionTree{
    private String id;

    private String parentId;

    private String name;

    private String description;

    private Integer weight;

    private Integer version;

    private List<PermissionTree> items;

    public void addItem(PermissionTree item){
        if(this.items == null){
            this.items = new LinkedList<>();
        }
        this.items.add(item);
    }
}
