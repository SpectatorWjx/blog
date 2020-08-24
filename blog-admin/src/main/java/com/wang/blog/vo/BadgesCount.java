package com.wang.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wjx
 */
@Data
public class BadgesCount{

    @ApiModelProperty("消息数量")
    private Integer messages;
}
