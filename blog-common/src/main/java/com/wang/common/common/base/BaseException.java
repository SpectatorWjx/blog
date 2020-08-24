package com.wang.common.common.base;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**错误码*/
    protected Integer errorCode;
    /**错误消息*/
    protected String errorMessage;

    public BaseException(Integer code, String msg) {
        super(msg);
        this.errorCode = code;
        this.errorMessage = msg;
    }
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.errorCode = code;
        this.errorMessage = String.format(msgFormat, args);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
