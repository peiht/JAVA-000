package com.javaAdvance.hmily.order.base;

/**
 * @author hitopei
 *
 * 返回状态码
 */
public enum ResCode {
    /**
     * 成功
     */
    SUCCESS(0, "success"),
    /**
     * 一般错误
     */
    ERROR(-1, "error"),
    /**
     * 系统错误
     */
    SYS_ERROR(500, "系统错误");
    /**
     * code
     */
    private final int code;
    /**
     * 返回信息
     */
    private final String msg;

    private ResCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
