package com.wang.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 */
@Data
public class CommentVO implements Serializable {
	private static final long serialVersionUID = 9192186139010913437L;

	private String id;
	/**
	 * 父评论ID
	 */
	private String pid;

	/**
	 * 所属内容ID
	 */
	private String postId;

	/**
	 * 评论内容
	 */
	private String content;

	private String authorId;

	private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	private Date createTime;

	private UserVO author;

	private CommentVO parent;

	private PostVO post;
}
