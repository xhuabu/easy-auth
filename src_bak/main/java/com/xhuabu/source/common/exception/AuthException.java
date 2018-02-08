package com.xhuabu.source.common.exception;


/**
 * Created by lee on 17/4/25.
 *
 * @CreatedBy lee
 * @Date 17/4/25
 */
public class AuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthException(String message) {
        super(message);
    }
}
