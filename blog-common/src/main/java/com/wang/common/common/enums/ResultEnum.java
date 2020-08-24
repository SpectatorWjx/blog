package com.wang.common.common.enums;

import lombok.Getter;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
@Getter
public enum ResultEnum {
    /**
     * 返回结果枚举
     */

    SUCCESS(200,"success"),
    FAILURE(500,"fail"),
    NO_VALUE(404,"页面不存在"),

    USER_NEED_AUTHORITIES(403,"用户未登录"),
    USER_NO_ACCESS(403,"用户无权访问"),


    /**
     * 公用
     */

    SERVICE_REQUEST_TIMEOUT(6000,"服务请求超时 "),
    SERVICE_REQUEST_ERROR(6001,"服务请求失败 "),
    PARAM_ERROR(6002,"参数错误"),

    USER_LOGIN_FAILED(1407,"密码错误"),
    TOKEN_IS_BLACKLIST(1407,"此token无效"),
    LOGIN_IS_OVERDUE(1407,"登录已失效"),
    ACCOUNT_IS_LOCKED(1408,"账号锁定"),

    SMS_CODE_ERROR(1102,"验证码错误"),
    SMS_CODE_IS_INVALID(1103,"验证码无效,重新获取"),
    SMS_CODE_NOT_SEND(1104,"请先获取验证码"),


    /**
     *  2****
     */
    SMS_SEND_FREQUENT(2101,"验证码发送频繁"),


    /**
     *  3****
     */
    FILE_UPLOAD_FAILED(3001,"文件保存失败")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @deprecation:通过code返回枚举
    */
    public static ResultEnum parse(int code){
        ResultEnum[] values = values();
        for (ResultEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        return null;
    }
}
