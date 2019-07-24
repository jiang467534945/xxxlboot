package com.xxxlboot.common.util;

import com.xxxlboot.common.base.FileResult;
import com.xxxlboot.common.base.Result;

/**
 * @auther: Easy
 * @Date: 18-9-6 01:20
 * @Description:Result生成工具类
 */
public class Results {
    public Results() {
    }

    public static Result newResult() {
        return new Result();
    }

    public static Result newResult(boolean success) {
        return new Result(success);
    }

    //
    //业务调用成功
    //    -------------------------------------------------------------------------------------------------

    /**
     * 功能描述: 不带参数返回成功
     *
     * @param: 无参返回成功
     * @return: null
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result success() {
        return new Result();
    }

    /**
     * 功能描述: 返回带描述
     *
     * @param: msg
     * @return: 返回成功带描述
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result success(String msg) {
        return new Result(true, null, msg);
    }

    /**
     * 功能描述: 带描述并返回编码格式
     *
     * @param: msg，code
     * @return: Result对象带编码以及描述
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result success(String code, String msg) {
        return new Result(true, code, msg);
    }

    /**
     * 功能描述: 返回成功状态码
     *
     * @param: status
     * @return: 返回成功状态
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result successWithStatus(Integer status) {
        return new Result(true, status);
    }

    /**
     * 功能描述: 返回成功状态码并带描述
     *
     * @param: status，msg
     * @return: Result对象带描述以及状态
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result successWithStatus(Integer status, String msg) {
        return new Result(true, status, null, msg);
    }

    /**
     * 功能描述: 成功并返回对象
     *
     * @param: data
     * @return: Result对象带数据对象
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result successWithData(Object data) {
        return new Result(true, null, null, data);
    }

    /**
     * 功能描述: 成功并返回对象以及描述
     *
     * @param: data，msg
     * @return: Result对象带数据对象和描述
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result successWithData(Object data, String msg) {
        return new Result(true, null, msg, data);
    }

    /**
     * 功能描述: 成功并返回对象以及描述以及描述编码
     *
     * @param: code，data，msg
     * @return: Result对象带数据对象和描述以及描述编码
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static Result successWithData(Object data, String code, String msg) {
        return new Result(true, code, msg, data);
    }

    //
    // 业务调用失败
    // ----------------------------------------------------------------------------------------------------

    /**
     * 功能描述: 不带参数返回失败
     *
     * @param: 无参
     * @return: Result对象参数为false
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failure() {
        return new Result(false);
    }

    /**
     * 功能描述: 带描述返回失败
     *
     * @param: msg
     * @return: Result对象参数为false，并返回失败原因
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failure(String msg) {
        return new Result(false, null, msg);
    }

    /**
     * 功能描述: 带描述返回失败，并返回编码
     *
     * @param: msg，code
     * @return: Result对象参数为false，并返回失败原因以及描述编码
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failure(String code, String msg) {
        return new Result(false, code, msg);
    }

    /**
     * 功能描述: 返回失败，并返回失败状态码
     *
     * @param: status
     * @return: Result对象参数为false，并包含状态码
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failureWithStatus(Integer status) {
        return new Result(false, status);
    }

    /**
     * 功能描述: 返回失败，并返回失败状态码以及失败描述
     *
     * @param: status，msg
     * @return: Result对象参数为false，并包含状态码和失败描述
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failureWithStatus(Integer status, String msg) {
        return new Result(false, status, null, msg);
    }

    /**
     * 功能描述: 返回失败，并返回数据对象
     *
     * @param: data
     * @return: Result对象参数为false，并包含data数据对象
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failureWithData(Object data) {
        return new Result(false, null, null, data);
    }

    /**
     * 功能描述: 返回失败，并返回数据对象以及失败描述
     *
     * @param: data，msg
     * @return: Result对象参数为false，并包含data数据对象以及失败描述
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failureWithData(Object data, String msg) {
        return new Result(false, null, msg, data);
    }

    /**
     * 功能描述: 返回失败，并返回数据对象以及失败描述
     *
     * @param: data，code，msg
     * @return: Result对象参数为false，并包含data数据对象以及失败描述以及描述编码
     * @auther: easy
     * @date: 18-9-6 上午1:32
     */
    public static Result failureWithData(Object data, String code, String msg) {
        return new Result(false, code, msg, data);
    }
    /**
     * 功能描述: 文件上传失败
     *
     * @param: msg
     * @return: 返回成功带描述
     * @auther: easy
     * @date: 18-9-6 上午1:33
     */
    public static FileResult fileSuccess(String label,String url) {
        return new FileResult(label, url);
    }
}
