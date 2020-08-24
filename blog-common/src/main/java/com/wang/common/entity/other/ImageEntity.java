package com.wang.common.entity.other;

import com.wang.common.common.base.TableIdPrefix;
import com.wang.common.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
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
@Table(name = "image")
@SQLDelete(sql = "update image set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@TableIdPrefix("IMGTAB")
@org.hibernate.annotations.Table(appliesTo = "image",comment="图片实体类")
public class ImageEntity extends BaseEntity {

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT '缩略图在mongo里Id'")
    private String compressImageId;

    @Column(columnDefinition="tinyint(1) DEFAULT null COMMENT '图片类别，归属于哪一类'")
    private String category;

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT '原图Id'")
    private String masterImageId;

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT 'mongoCollection'")
    private String mongoCollectionName;

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT '文件名称'")
    private String name;

    @Column(columnDefinition="int(11) DEFAULT null COMMENT '文件大小'")
    private Long size;

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT '文件类型'")
    private String type;

    @Column(columnDefinition="varchar(50) DEFAULT null COMMENT '文件后缀名'")
    private String suffix;
}
