package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.search.annotations.*;

import javax.persistence.Index;
import javax.persistence.*;
import java.util.Date;

/**
 * 内容表
 * @author wjx
 *
 */
@Data
@Entity
@Table(name = "blog_post", indexes = {
		@Index(name = "IK_CHANNEL_ID", columnList = "channel_id")
})
@FilterDefs({
		@FilterDef(name = "POST_STATUS_FILTER", defaultCondition = "status = 0" )})
@Filters({ @Filter(name = "POST_STATUS_FILTER") })
@Indexed(index = "post")
@TableIdPrefix("POSTTB")
@Analyzer(impl = SmartChineseAnalyzer.class)
@org.hibernate.annotations.Table(appliesTo = "blog_post",comment="文章类")
public class PostEntity extends BaseEntity {

	/**
	 * 分组/模块ID
	 */
	@Field
	@NumericField
	@Column(name = "channel_id", length = 5)
	private String channelId;

	/**
	 * 标题
	 */
	@Field
	@Column(name = "title", length = 64)
	private String title;

	/**
	 * 摘要
	 */
	@Field
	@Column(length = 140)
	private String summary;

	/**
	 * 预览图
	 */
	@Column(length = 128)
	private String thumbnail;

	/**
	 * 标签, 多个逗号隔开
	 */
	@Field
	@Column(length = 64)
	private String tags;

	@Field
	@NumericField
	@Column(name = "author_id")
	private String authorId;

	/**
	 * 收藏数
	 */
	private Integer favors = 0;

	/**
	 * 评论数
	 */
	private Integer comments = 0;

	/**
	 * 阅读数
	 */
	private Integer views = 0;

	/**
	 * 文章状态
	 */
	private Integer status = 0;

	/**
	 * 推荐状态
	 */
	private Integer featured = 0;

	/**
	 * 置顶状态
	 */
	private Integer weight = 0;
}