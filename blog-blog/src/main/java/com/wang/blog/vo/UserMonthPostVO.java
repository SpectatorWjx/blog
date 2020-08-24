package com.wang.blog.vo;

import lombok.Data;

import java.util.List;

/***
 * @classname UserYearPostVo
 * @description
 * @author wjx
 * @date 2020/7/9 13:45
 */
@Data
public class UserMonthPostVO {
    private String month;

    private List<PostVO> postVOList;
}
