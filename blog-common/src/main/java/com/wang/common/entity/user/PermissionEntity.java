package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 权限值
 * @author - wjx on 2018/2/11
 */
@Data
@Entity
@Table(name = "shiro_permission")
@TableIdPrefix("PRIMIS")
@org.hibernate.annotations.Table(appliesTo = "shiro_permission",comment="权限类")
public class PermissionEntity extends BaseEntity {

    @Column(name = "parent_id", updatable = false)
    private String parentId;
    
    @Column(nullable = false, unique = true, length = 32)
    private String name;

    @Column(length = 140)
    private String description;

    private Integer weight;

    @Version
    private Integer version;
}
