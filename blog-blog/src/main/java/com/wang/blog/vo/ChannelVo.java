package com.wang.blog.vo;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 模块/内容分组
 * @author wjx
 * @date 2019/08/13
 */
@Data
public class ChannelVo{

	private String name;

	private String key;

	private String thumbnail;

	private Integer weight;
}
