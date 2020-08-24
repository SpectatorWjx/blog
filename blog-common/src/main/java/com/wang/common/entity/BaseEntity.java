package com.wang.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/***
 * @classname BaseEntity
 * @description
 * @author wjx zhijiu
 * @date 2019/4/27
 */
@Data
//@DynamicUpdate //动态赋值
//@DynamicInsert
//表明这是父类，可以将属性映射到子类中使用JPA生成表
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
    @Id
    @Column(name = "id",length = 50,nullable = false, columnDefinition="varchar(50) COMMENT '主键Id'")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "base-id")
    @GenericGenerator(name = "base-id", strategy = "com.wang.common.common.base.IdGenerator")
    protected String id;

    /**
     * 删除标记（0：正常；1：删除；2：审核）
     *
     */
    @JsonIgnore
    @Column(columnDefinition="tinyint(1) DEFAULT 0 COMMENT '删除标记（0：正常；1：删除；2：审核）'")
    protected Integer delFlag;

    public BaseEntity() {
        super();
        this.delFlag = 0;
    }


    /**
     * 创建日期
     * @CreationTimestamp - 创建时自动更新时间
     */
    @Column(updatable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    protected Timestamp createTime;

    /**
     * 更新日期
     * @UpdateTimestamp - 更新时自动更新时间
     */
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    protected Timestamp updateTime;


    /**
     * 创建者
     * @CreatedBy
     */
    @JsonIgnore
    @Column(updatable = false, length = 32,columnDefinition="char(32) COMMENT '创建者'")
    @CreatedBy
    protected String createBy;

    /**
     * 更新者
     *
     * @LastModifiedBy
     */
    @JsonIgnore
    @Column(length = 32,columnDefinition="varchar(32) COMMENT '更新人'")
    @LastModifiedBy
    protected String updateBy;

    /**
     * 非数据库字段,查询用
     */
    @Transient
    @JsonIgnore
    protected String search;
    /**
     * 非数据库字段,查询用
     */
    @Transient
    @JsonIgnore
    protected String startTime;
    /**
     * 非数据库字段,查询用
     */
    @Transient
    @JsonIgnore
    protected String endTime;

}
