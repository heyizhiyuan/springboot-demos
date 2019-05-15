package com.cnj.protocol.result;

import lombok.Getter;
import lombok.ToString;

/**
 * Create by cnj on 2019-5-14
 */
@Getter
@ToString
public class ResultEntity<T> {

    private final static String SUCCESS = "ok";

    private final static String FAIL = "fail";

    private String message;

    private boolean success;

    private T data;

    private ResultEntity(){
        this.success = true;
        this.message = SUCCESS;
    }

    private final ResultEntity<T> setMessage(String message){
        this.message = message;
        return this;
    }

    private final ResultEntity<T> setSuccess(boolean success){
        this.success = success;
        return this;
    }

    private final ResultEntity<T> setData(T data){
        return this;
    }

    /**
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(T data,String message){
        return new ResultEntity<T>().setSuccess(true).setData(data).setMessage(message);
    }

    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(T data){
        return success(data,SUCCESS);
    }

    /**
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(String message){
        return success(null,message);
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> success(){
        return success(SUCCESS);
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> fail(){

        return fail(FAIL);
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResultEntity<T> fail(String message){
        return new ResultEntity<T>().setSuccess(false).setMessage(message);
    }

}
