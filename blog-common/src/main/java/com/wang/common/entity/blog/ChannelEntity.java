package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 模块/内容分组
 * @author wjx
 *
 */
@Data
@Entity
@Table(name = "blog_channel")
@TableIdPrefix("CHANEL")
@org.hibernate.annotations.Table(appliesTo = "blog_channel",comment="分组类")
public class ChannelEntity extends BaseEntity {

	/**
	 * 名称
	 */
	@Column(length = 32)
	private String name;

	/**
	 * 唯一关键字
	 */
	@Column(name = "key_", unique = true, length = 32)
	private String key;

	/**
	 * 预览图
	 */
	@Column(length = 128)
	private String thumbnail;

	@Column(length = 5)
	private Integer status = 0;

	/**
	 * 排序值
	 */
	private Integer weight = 0;
}
