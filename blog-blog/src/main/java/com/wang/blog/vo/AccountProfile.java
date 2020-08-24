package com.wang.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class AccountProfile implements Serializable {
    private static final long serialVersionUID = 1748764917028425871L;
    private String id;

    private String username;

    private String avatar;

    private String name;

    private String email;

    private Date lastLogin;

    private Integer status;

    private BadgesCount badgesCount;

    public AccountProfile(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
