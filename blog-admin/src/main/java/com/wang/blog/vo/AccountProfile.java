package com.wang.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 *
 */
@Data
public class AccountProfile implements Serializable {
    private static final long serialVersionUID = -1825254785425611949L;
    private String id;
    private String username;
    private String avatar;
    private String name;
    private String email;

    private Date lastLogin;
    private int status;

    private BadgesCount badgesCount;

    public AccountProfile(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
