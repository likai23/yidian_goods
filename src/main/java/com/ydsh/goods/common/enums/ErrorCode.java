package com.ydsh.goods.common.enums;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:23
 **/
public enum ErrorCode {
    SYS_EXCEPTION(9000, "系统异常,操作失败"),
    ILLEGAL_ARGUMENT(9001, "参数非法");
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code){
        for(ErrorCode errorCode:ErrorCode.values()){
            if(code.equals(errorCode.getCode())){
                return errorCode.getMsg();
            }
        }
        return  null;
    }
}
