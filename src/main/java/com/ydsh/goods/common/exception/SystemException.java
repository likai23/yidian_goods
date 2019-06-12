package com.ydsh.goods.common.exception;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:25
 **/
public class SystemException extends CommonException {
    public SystemException(Integer code, String message, Exception e) {
        super(code, message, e);
    }

    public SystemException(Exception e) {
        super(e);
    }
}
