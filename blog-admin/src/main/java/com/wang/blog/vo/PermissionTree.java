package com.wang.blog.vo;

import com.wang.common.entity.user.PermissionEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author - wjx on 2018/2/11
 */
public class PermissionTree extends PermissionEntity {
    private List<PermissionTree> items;

    public List<PermissionTree> getItems() {
        return items;
    }

    public void setItems(List<PermissionTree> items) {
        this.items = items;
    }

    public void addItem(PermissionTree item){
        if(this.items == null){
            this.items = new LinkedList<>();
        }
        this.items.add(item);
    }
}
