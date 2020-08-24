package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色映射表
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
@Entity
@Table(name = "shiro_user_role")
@TableIdPrefix("USEROL")
@org.hibernate.annotations.Table(appliesTo = "shiro_user_role",comment="用户角色类")
public class UserRoleEntity extends BaseEntity {

	@Column(name = "user_id")
	private String userId;

	@Column(name = "role_id")
    private String roleId;
}
