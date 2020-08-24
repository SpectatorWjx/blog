package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色-权限值
 * @author - wjx on 2018/2/11
 */
@Data
@Entity
@Table(name = "shiro_role_permission")
@TableIdPrefix("ROLEPM")
@org.hibernate.annotations.Table(appliesTo = "shiro_role_permission",comment="角色权限类")
public class RolePermissionEntity extends BaseEntity {

    @Column(name = "role_id")
    private String roleId;


    @Column(name = "permission_id")
    private String permissionId;
}
