package com.ydsh.goods.common.exception;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:25
 **/
public class CommonException extends RuntimeException {
    protected Integer code;

    public Integer getCode() {
        return code;
    }

    protected CommonException(Integer code, String message, Exception e) {
        super(message, e);
        this.code = code;
    }

    protected CommonException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    protected CommonException(Exception e) {
        super(e);
    }
}
