package com.don.donaldblog.utils;


import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyCaptchaFilter extends OncePerRequestFilter {
    private AuthenticationFailureHandler authenticationFailureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/auth/login-in") && request.getMethod().equalsIgnoreCase("post")) {
            try {
                VerifyCode(request);
            } catch (VerifyCaptchaException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void VerifyCode(HttpServletRequest request) throws ServletRequestBindingException {
        String code = request.getParameter("code");
        String sessionCode = request.getSession().getAttribute("verifyCode").toString();
        if (!code.equalsIgnoreCase(sessionCode)) {
            throw new VerifyCaptchaException("verify_error");
        }
        request.getSession().removeAttribute("verifyCode");
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
