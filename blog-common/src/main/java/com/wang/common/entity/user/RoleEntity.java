package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 角色
 * @author - wjx on 2018/2/11
 */
@Data
@Entity
@Table(name = "shiro_role")
@TableIdPrefix("ROLETB")
@org.hibernate.annotations.Table(appliesTo = "shiro_role",comment="角色类")
public class RoleEntity extends BaseEntity {

    public static Integer STATUS_NORMAL = 0;
    public static Integer STATUS_CLOSED = 1;

    public static String ROLE_ADMIN = "blog";

    public static Long ADMIN_ID = 1L;



    @Column(nullable = false, updatable = false, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    private Integer status = 0;

    @Transient
    private List<PermissionEntity> permissions;

}
