package com.wang.blog.vo;

import lombok.Data;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class FavoriteVO {

    private String id;

    private String userId;

    private String postId;

    private PostVO post;
}
