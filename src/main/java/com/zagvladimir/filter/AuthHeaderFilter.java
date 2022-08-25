package com.zagvladimir.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthHeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("In Auth filter!");
//
//        HttpServletRequest castedRequest = (HttpServletRequest) request;
//
//        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
//        for (Cookie cookie : cookies) {
//            System.out.println(cookie.getSecure());
//        }
//
//        //String authHeader = castedRequest.getHeader("X-Auth-Token");
//        String authHeader = castedRequest.getHeader("Secret-Key");
//        if (StringUtils.isNotBlank(authHeader)) { //for future JWT Token Auth
//            System.out.println("Header was found with payload: " + authHeader);
//        } else {
//            System.out.println("Header was not found!");
//        }

        chain.doFilter(request, response);
    }
}
