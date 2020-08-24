package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统配置
 * @author wjx
 *
 */
@Data
@Entity
@Table(name = "blog_options")
@TableIdPrefix("OPTION")
@org.hibernate.annotations.Table(appliesTo = "blog_options",comment="配置类")
public class OptionsEntity extends BaseEntity {

	@Column(length = 5)
	private Integer type;
	
	@Column(name = "key_", unique = true, length = 32)
	private String key;

	@Column(length = 300)
	private String value;
}
