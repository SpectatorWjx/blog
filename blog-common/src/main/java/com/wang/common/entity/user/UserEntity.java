package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
@Entity
@Table(name = "shiro_user")
@TableIdPrefix("USERTB")
@org.hibernate.annotations.Table(appliesTo = "shiro_user",comment="user类")
public class UserEntity extends BaseEntity {

	/**
	 *用户名
	 */
	@Column(name = "username", unique = true, nullable = false, length = 64)
	private String username;

	/**
	 * 密码
	 */
	@Column(name = "password", length = 64)
	private String password;

	/**
	 * 密码
	 */
	@Column(name = "open_id", length = 64)
	private String openId;


	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 昵称
	 */
	@Column(name = "name", length = 18)
	private String name;

	private String state;

	@Column(name = "email", unique = true, length = 64)
	private String email;

	private Integer posts;

	private Integer comments;

	@Column(name = "last_login")
	private Date lastLogin;

	/**
	 * 个性签名
	 */
	private String signature;

	/**
	 * 用户状态
	 */
	private Integer status;

	public UserEntity() {

	}

	public UserEntity(String id) {
		setId(id);
	}
}
