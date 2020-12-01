package com.wang.blog.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wjx
 * @date 2019/12/10
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 107193816173103116L;

	private String id;

	private String username;

	private String avatar;

	private String name;

	private String email;

	private Integer posts;

	private Integer comments;

	private String signature;
}
