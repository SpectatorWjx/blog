package com.wang.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
@Data
public class BadgesCount implements Serializable {
    private static final long serialVersionUID = 8276459939240769498L;

    @ApiModelProperty("消息数量")
    private Integer messages;
}
