package com.wang.common.entity.other;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * @classname: FileName
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/28 8:41
 */
@Data
@Entity
@Table(name = "link")
@TableIdPrefix("LINKTB")
@org.hibernate.annotations.Table(appliesTo = "link",comment="友链类")
public class LinkEntity extends BaseEntity {

    private Integer linkType;

    private String linkName;

    private String linkUrl;

    private String linkDescription;

    private Integer linkRank;
}