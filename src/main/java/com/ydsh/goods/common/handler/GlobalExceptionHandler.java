package com.ydsh.goods.common.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ydsh.goods.common.bean.Result;
import com.ydsh.goods.common.enums.ErrorCode;
import com.ydsh.goods.common.exception.BizException;
import com.ydsh.goods.common.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:25
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result otherExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        Result result = new Result();
        result.setCode(ErrorCode.SYS_EXCEPTION.getCode());
        result.setMsg(ErrorCode.SYS_EXCEPTION.getMsg());
        log.error("==>其他异常:" + e.getMessage(), e);
        return result;
    }

    @ExceptionHandler(value = {BizException.class, SystemException.class,IllegalArgumentException.class})
    @ResponseBody
    public Result businessExceptionHandler(HttpServletRequest req, Exception e) {
        Result result = new Result();
        if (e instanceof BizException) {
            BizException biz = (BizException) e;
            result.setMsg(biz.getMessage());
            result.setCode(biz.getCode());
            log.error("==>业务错误代码:{},错误消息:{}", biz.getCode(), biz.getMessage());
        }
        if (e instanceof SystemException) {
            SystemException systemException = (SystemException) e;
            result.setMsg(systemException.getMessage());
            result.setCode(systemException.getCode());
            log.error("==>程序错误代码:{},错误消息:{}", systemException.getCode(), systemException.getMessage());
        }
        if (e instanceof IllegalArgumentException){
            IllegalArgumentException illegalArgumentException = (IllegalArgumentException) e;
            result.setMsg(illegalArgumentException.getMessage());
            result.setCode(ErrorCode.ILLEGAL_ARGUMENT.getCode());
            log.error("==>参数错误代码:{},错误消息:{}", ErrorCode.ILLEGAL_ARGUMENT.getCode(), illegalArgumentException.getMessage());
        }
        return result;
    }



}
