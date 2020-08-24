package com.wang.blog.vo;

import lombok.Data;

/***
 * @classname: ImageVo
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/31 16:12
 */
@Data
public class ImageVo {
    private String id;
    private String compressImageId;
    private String category;
    private String masterImageId;
    private String mongoCollectionName;
}
