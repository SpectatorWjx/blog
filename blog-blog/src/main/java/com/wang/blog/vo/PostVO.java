package com.wang.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wang.blog.base.lang.Consts;
import com.wang.common.entity.blog.PostAttributeEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class PostVO implements Serializable {
	private static final long serialVersionUID = -1144627551517707139L;

	private String id;

	private Date createTime;

	private String createDay;

	/**
	 * 分组/模块ID
	 */
	private String channelId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 预览图
	 */
	private String thumbnail;

	/**
	 * 标签, 多个逗号隔开
	 */
	private String tags;


	private String authorId;

	/**
	 * 收藏数
	 */
	private Integer favors;

	/**
	 * 评论数
	 */
	private Integer comments;

	/**
	 * 阅读数
	 */
	private Integer views;

	/**
	 * 文章状态
	 */
	private Integer status;

	/**
	 * 推荐状态
	 */
	private Integer featured;

	/**
	 * 置顶状态
	 */
	private Integer weight;


	private String editor;

	private String content;

	private UserVO author;

	private String channelName;
	
	@JSONField(serialize = false)
	private PostAttributeEntity attribute;
	
	public String[] getTagsArray() {
		if (StringUtils.isNotBlank(getTags())) {
			return getTags().split(Consts.SEPARATOR);
		}
		return null;
	}
}
