package com.wang.blog.vo;


import lombok.Data;

/**
 * 系统配置
 * @author wjx
 *
 */
@Data
public class OptionVO {
    private String site_name;
    private String site_domain;
    private String site_keywords;
    private String site_description;
    private String site_metas;
    private String site_copyright;
    private String site_icp;
    private String qq_callback;
    private String qq_app_id;
    private String qq_app_key;
    private String weibo_callback;
    private String weibo_client_id;
    private String weibo_client_sercret;
    private String github_callback;
    private String github_client_id;
    private String github_secret_key;
    private String gitee_callback;
    private String gitee_client_id;
    private String gitee_secret_key;
    private String alipay_callback;
    private String alipay_client_id;
    private String alipay_secret_key;
    private String theme;
    private String site_logo;
    private String editor;
    private String site_favicon;
    private String site_background;
    private String site_post_background;
    private String site_banner;
    private String site_version;
    private String mail_smtp_host;
    private String mail_smtp_username;
    private String mail_smtp_password;

    private String storage_scheme;
    private String upyun_oss_bucket;
    private String upyun_oss_operator;
    private String upyun_oss_password;
    private String upyun_oss_domain;
    private String upyun_oss_src;
}
