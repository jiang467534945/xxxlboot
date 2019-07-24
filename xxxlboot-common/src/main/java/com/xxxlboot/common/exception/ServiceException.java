package com.xxxlboot.common.exception;

/**
 * @auther: Easy
 * @Date: 18-9-6 17:31
 * @Description:Service层异常
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 6058294324031642376L;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }

}