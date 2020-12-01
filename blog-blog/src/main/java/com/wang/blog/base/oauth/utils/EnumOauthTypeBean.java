package com.wang.blog.base.oauth.utils;


/**
 * @author wjx
 * @date 2019/12/10
 */
public enum EnumOauthTypeBean {
    /**
     *QQ
     */
    TYPE_QQ("QQ登陆", 1),

    /**
     *微博
     */
    TYPE_SINA("微博登陆", 2),

    /**
     *GitHub
     */
    TYPE_GITHUB("github登陆", 3),

    /**
     *Gitee
     */
    TYPE_GITEE("gitee登陆", 4),

    /**
     *ali_pay
     */
    TYPE_ALI_PAY("支付宝登陆", 5);

    private String description;
    private int value;

    EnumOauthTypeBean(String desc, int value) {
        this.description = desc;
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumOauthTypeBean getEnumStatus(int type) throws Exception {
        EnumOauthTypeBean[] status = values();
        for (int i = 0; i < status.length; i++) {
            if (status[i].getValue() == type) {
                return status[i];
            }
        }

        throw new Exception();
    }
}
