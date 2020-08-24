package com.wang.common.common.enums;


import lombok.Getter;

/***
 * @author: wjx zhijiu
 * @date: 2019/12/3 15:40
 */
@Getter
public enum MessageStatusEnum {
    /**
     * 消息状态枚举
     */
    UN_READ(0,"未读"),
    READ(1,"已读")
    ;

    private Integer status;

    private String message;

    MessageStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * 返回枚举
     */
    public static MessageStatusEnum parse(Integer status){
        MessageStatusEnum[] values = values();
        for (MessageStatusEnum value : values) {
            if(value.getStatus().equals(status)){
                return value;
            }
        }
        return null;
    }
}
