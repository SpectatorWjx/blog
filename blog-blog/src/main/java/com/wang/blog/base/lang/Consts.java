package com.wang.blog.base.lang;


/**
 * @author wjx
 * @date 2019/12/10 11:58
 */
public interface Consts {

	/**
	 * 默认头像
	 */
	String AVATAR = "https://delpast.com/image/thumbnail/IMGTAB_3f4b2753d81f4ee1a4e1b7903eb2e967";

	/**
	 * id 加密
	 */

	byte[] userIdKeys = new byte[]{1, 2};


	/**
	 * 账号已激活
	 */
	int ACTIVE = 0;

	/**
	 * 分隔符
	 */
	String SEPARATOR = ",";

	String SEPARATOR_X = "x";

	String ROLE_ADMIN = "blog";

	int PAGE_DEFAULT_SIZE = 10;

	/**
	 * 自增步进
	 */
	int IDENTITY_STEP = 1;

	/**
	 *  递减
	 */
	int DECREASE_STEP = -1;

	/**
	 *最小时间单位, 1秒
	 */
	int TIME_MIN = 1000;

	// 忽略值
	int IGNORE = -1;

	int ZERO = 0;

	// 禁用状态
	int STATUS_CLOSED = 1;

	/* 状态-初始 */
	int STATUS_NORMAL = 0;

	/* 状态-推荐 */
	int STATUS_FEATURED = 1;

	/* 状态-锁定 */
	int STATUS_LOCKED = 1;

	int STATUS_HIDDEN = 1;

	/**
	 * 排序
	 */
	interface order {
		String FEATURED = "featured";
		String NEWEST = "newest";
		String HOTTEST = "hottest";
		String FAVOR = "favors";
	}

	/**
	 * bind email
	 */
	int CODE_BIND = 1;

	/**
	 *forgot password
	 */
	int CODE_FORGOT = 2;
	int CODE_REGISTER = 3;

	/**
	 * 验证码-初始
	 */
	int CODE_STATUS_INIT = 0;

	/**
	 * 验证码-已使用
	 */
	int CODE_STATUS_CERTIFIED = 1;

	/**
	 * 推荐状态-默认
	 */
	int FEATURED_DEFAULT = 0;

	/**
	 * 推荐状态-推荐
	 */
	int FEATURED_ACTIVE = 1;

	/**
	 * 未读
	 */
	int UNREAD = 0;

	/**
	 * 已读
	 */
	int READED = 1;


	/**
	 * 有人喜欢了你的文章
	 */
	int MESSAGE_EVENT_FAVOR_POST = 1;


	/**
	 * 有人评论了你
	 */
	int MESSAGE_EVENT_COMMENT = 3;


	/**
	 * 有人回复了你
	 */
	int MESSAGE_EVENT_COMMENT_REPLY = 4;

	String CACHE_USER = "userCaches";
	String CACHE_POST = "postCaches";

	/**
	 * 第三方回调配置
	 */
	/**
	 * // 第三方登陆-QQ回调地址// QQ互联APP_ID		// QQ互联APP_KEY
	 */
	String QQ_CALLBACK = "qq_callback";
	String QQ_APP_ID = "qq_app_id";
	String QQ_APP_KEY = "qq_app_key";

	/**
	 *  // 第三方登陆-微博回调地址	// 微博应用CLIENT_ID	// 微博应用CLIENT_SERCRET
	 */
	String WEIBO_CALLBACK = "weibo_callback";
	String WEIBO_CLIENT_ID = "weibo_client_id";
	String WEIBO_CLIENT_SERCRET = "weibo_client_sercret";


	/**
	 * 	// 第三方登陆-github回调地址//github应用CLIENT_ID//github应用SECRET_KEY
	 */
	String GITHUB_CALLBACK = "github_callback";
	String GITHUB_CLIENT_ID = "github_client_id";
	String GITHUB_SECRET_KEY = "github_secret_key";

	/**
	 * 	// 第三方登陆-gitee回调地址//gitee应用CLIENT_ID//gitee应用SECRET_KEY
	 */
	String GITEE_CALLBACK = "gitee_callback";
	String GITEE_CLIENT_ID = "gitee_client_id";
	String GITEE_SECRET_KEY = "gitee_secret_key";

	/**
	 * 	// 第三方登陆-支付宝回调地址//支付宝应用CLIENT_ID//支付宝应用SECRET_KEY
	 */
	String ALIPAY_CALLBACK = "alipay_callback";
	String ALIPAY_CLIENT_ID = "alipay_client_id";
	String ALIPAY_SECRET_KEY = "alipay_secret_key";

	String EMAIL_TEMPLATE_CODE = "email_code.ftl";

	String EMAIL_TEMPLATE_ACTIVE = "email_active.ftl";

	String EDITOR_MARKDOWN = "markdown";

	String STORAGE_LIMIT_SIZE = "storage_limit_size";
	String STORAGE_MAX_WIDTH = "storage_max_width";

}
