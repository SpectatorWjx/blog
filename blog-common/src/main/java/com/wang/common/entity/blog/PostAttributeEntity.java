package com.wang.common.entity.blog;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
@Entity
@Table(name = "blog_post_attribute")
@TableIdPrefix("POSTAT")
@org.hibernate.annotations.Table(appliesTo = "blog_post_attribute",comment="文章类")
public class PostAttributeEntity implements Serializable{
    private static final long serialVersionUID = 9192186139010913437L;

    @Id
    @Column(name = "id",length = 50,nullable = false, columnDefinition="varchar(50) COMMENT '主键Id'")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "base-id")
    @GenericGenerator(name = "base-id", strategy = "com.wang.common.common.base.IdGenerator")
    private String id;

    @Column(name = "post_id", length = 255)
    private String postId;


	@Column(length = 16, columnDefinition = "varchar(16) default 'tinymce'")
	private String editor;

    /**
     * 内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    private String content;

}
