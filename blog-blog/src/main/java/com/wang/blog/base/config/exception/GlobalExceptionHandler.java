package com.wang.blog.base.config.exception;

import com.wang.blog.web.controller.site.Views;
import com.wang.common.common.base.BaseException;
import com.wang.common.common.base.Result;
import com.wang.common.common.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;

/***
 * @classname:
 * @description:
 * @author: wjx zhijiu
 * @date: 2019/10/24 10:29
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements HandlerInterceptor {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result defaultErrorHandler(Exception ex) {
        log.error(ex.toString());
        if (ex instanceof BaseException) {
            return Result.exception(((BaseException) ex).getErrorCode(), ((BaseException) ex).getErrorMessage());
        }else if (ex instanceof BindException) {
            ex.printStackTrace();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("参数错误：");
            ((BindException) ex).getAllErrors().forEach(e -> stringBuffer.append(e.getDefaultMessage()).append(","));
            return Result.exception(ResultEnum.PARAM_ERROR.getCode(), stringBuffer.toString());
        }else if(ex instanceof MethodArgumentNotValidException){
            ex.printStackTrace();
            Object errorParam = ((MethodArgumentNotValidException) ex).getBindingResult().getTarget();
            log.error(errorParam.toString());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("参数错误：");
            ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach(e -> stringBuffer.append(e.getDefaultMessage()).append(","));
            return Result.exception(ResultEnum.PARAM_ERROR.getCode(), stringBuffer.toString());
        }else if (ex instanceof HttpRequestMethodNotSupportedException) {
            ex.printStackTrace();
            return Result.exception(ResultEnum.FAILURE.getCode(), "请求方式错误");
        }else if (ex instanceof HttpMediaTypeNotSupportedException) {
            ex.printStackTrace();
            return Result.exception(ResultEnum.FAILURE.getCode(), "上传文件格式错误");
        }else if (ex instanceof RuntimeException) {
            ex.printStackTrace();
            return Result.exception(ResultEnum.FAILURE.getCode(), ex.getMessage()+"运行时异常");
        } else {
            ex.printStackTrace();
            return Result.exception(ResultEnum.FAILURE.getCode(), ex.getMessage()+"系统错误");
        }
    }

}