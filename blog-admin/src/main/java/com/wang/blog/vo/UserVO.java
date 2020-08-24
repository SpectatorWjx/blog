package com.wang.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wang.common.entity.user.RoleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wjx
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 107193816173103116L;

	private String id;
	private String username;

	@JSONField(serialize = false)
	private String password;
	private String avatar;
	private String name;
	private String email;

	private int posts;

	private int comments;

	private Date lastLogin;

	private String signature;

	private int status;

	@JSONField(serialize = false)
	private List<RoleEntity> roles = new ArrayList<>();
}
