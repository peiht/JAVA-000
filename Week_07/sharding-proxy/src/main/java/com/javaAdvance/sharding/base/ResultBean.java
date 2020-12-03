package com.javaAdvance.sharding.base;


import java.io.Serializable;

/**
 * 标准返回题
 *
 * @author hitopei
 * @param <T>
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1430633339880116031L;


    private Integer code;

    private String msg;

    private T data;

    public ResultBean(){

    }

    public ResultBean (Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBean (Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultBean<T> success(){
        return new ResultBean<T>(ResCode.SUCCESS.code(), ResCode.SUCCESS.msg());
    }

    public static <T> ResultBean<T> success(Integer code, String msg) {
        return new ResultBean<T>(code, msg);
    }

    public static <T> ResultBean<T> success(Integer code, String msg, T data) {
        return new ResultBean<>(code, msg, data);
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(ResCode.SUCCESS.code(), "success", data);
    }

    public static <T> ResultBean<T> error(){
        return new ResultBean<>(ResCode.ERROR.code(), ResCode.ERROR.msg());
    }

    public static <T> ResultBean<T> error(String msg) {
        return new ResultBean<>(ResCode.SYS_ERROR.code(), msg);
    }

    public static <T> ResultBean<T> error(Integer code, String msg) {
        return new ResultBean<T>(code, msg);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
