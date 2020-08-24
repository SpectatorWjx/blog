package com.wang.blog.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.wang.blog.base.lang.Consts;
import com.wang.common.entity.blog.ChannelEntity;
import com.wang.common.entity.blog.PostAttributeEntity;
import com.wang.common.entity.blog.PostEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wjx
 * 
 */
public class PostVO extends PostEntity implements Serializable {
	private static final long serialVersionUID = -1144627551517707139L;

	private String editor;
	private String content;

	private UserVO author;
	private ChannelEntity channel;

	@JSONField(serialize = false)
	private PostAttributeEntity attribute;
	
	public String[] getTagsArray() {
		if (StringUtils.isNotBlank(super.getTags())) {
			return super.getTags().split(Consts.SEPARATOR);
		}
		return null;
	}

	public UserVO getAuthor() {
		return author;
	}

	public void setAuthor(UserVO author) {
		this.author = author;
	}

	public PostAttributeEntity getAttribute() {
		return attribute;
	}

	public void setAttribute(PostAttributeEntity attribute) {
		this.attribute = attribute;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ChannelEntity getChannel() {
		return channel;
	}

	public void setChannel(ChannelEntity channel) {
		this.channel = channel;
	}
}
