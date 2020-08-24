package com.wang.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class CommentVO implements Serializable {
	private static final long serialVersionUID = 9192186139010913437L;

	private String id;

	@ApiModelProperty("父评论ID")
	private String pid;

	@ApiModelProperty("所属内容ID")
	private String postId;

	@ApiModelProperty("评论内容")
	private String content;

	private String authorId;

	private String createTime;

	private UserVO author;

	private CommentVO parent;

	private PostVO post;
}
