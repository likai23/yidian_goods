package com.ydsh.goods.common.enums;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:23
 **/
public enum SuccessCode {
    SYS_SUCCESS(200, "操作成功"),
    
    ;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    SuccessCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMsgByCode(String code){
        for(SuccessCode errorCode:SuccessCode.values()){
            if(code.equals(errorCode.getCode())){
                return errorCode.getMsg();
            }
        }
        return  null;
    }
}
