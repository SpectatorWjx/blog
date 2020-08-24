package com.wang.common.entity.user;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证码
 * @author wjx on 2015/8/14.
 */
@Data
@Entity
@Table(name = "shiro_security_code")
@TableIdPrefix("SECODE")
@org.hibernate.annotations.Table(appliesTo = "shiro_security_code",comment="授权code类")
public class SecurityCodeEntity extends BaseEntity {

    /**
     *  用户ID
     */
    @Column(name = "key_", unique = true, nullable = false, length = 64)
    private String key;

    /**
     *  验证码
     */
    @Column(length = 16, nullable = false)
    private String code;

    /**
     *  目标：邮箱
     */
    @Column(length = 64)
    private String target;

    /**
     *  验证类型：注册验证、找回密码验证
     */
    @Column
    private Integer type;

    /**
     * 过期时间
     */
    @Column(name = "expired", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date expired;


    /**
     *  状态：正常、关闭
     */
    @Column
    private Integer status;
}
