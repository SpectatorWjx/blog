package com.wang.blog.vo;

import lombok.Data;

import java.util.List;

/***
 * @classname UserYearPostVo
 * @description
 * @author wjx
 * @date 2020/7/9
 */
@Data
public class UserYearPostVO {
    private String year;

    private List<UserMonthPostVO> monthPostVoList;
}
