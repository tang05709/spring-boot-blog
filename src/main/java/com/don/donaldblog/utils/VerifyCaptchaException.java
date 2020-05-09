package com.don.donaldblog.utils;

import org.springframework.security.authentication.BadCredentialsException;

public class VerifyCaptchaException extends BadCredentialsException {

    public VerifyCaptchaException(String msg) {
        super(msg);
    }
}
