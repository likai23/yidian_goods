package com.ydsh.goods.common.bean;

import lombok.Data;

/**
 * @Auth yaozhongjie
 * @Date 2019/6/2 11:22
 **/
@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(){
    }

    public Result(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Result(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
