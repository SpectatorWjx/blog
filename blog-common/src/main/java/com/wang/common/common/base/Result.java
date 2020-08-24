package com.wang.common.common.base;

import com.wang.common.common.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
@Data
@ApiModel
public class Result<T>{

    private Integer code;

    private String message;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Result() {
        this(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage());
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMessage();
        this.data = data;
    }

    /**
     * 请求成功返回
     * @param object
     * @return
     */
    public static <T> Result<T> success(T object){
        return new Result(object);
    }
    public static <T> Result<T> success(){
        return new Result();
    }

    public static <T> Result<T> failure(String resultResult){
        return new Result(500, resultResult);
    }

    public static <T> Result<T> exception(Integer code,String resultResult){
        return new Result(code, resultResult);
    }
}