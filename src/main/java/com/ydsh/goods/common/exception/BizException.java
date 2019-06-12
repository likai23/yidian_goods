package com.ydsh.goods.common.exception;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:25
 **/
public class BizException extends CommonException {
    public BizException(Integer code, String message) {
        super(code, message);
    }

    public BizException(Integer code, String message, Exception e) {
        super(code, message, e);
    }
}
