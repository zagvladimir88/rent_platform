package com.zagvladimir.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "CharsetFilter")
public class CharsetFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * Method change the encoding of request and response.
     * Then they will be transferred to a method for performing a next filter in the chain.
     *
     * @param request  - ServletRequest
     * @param response - ServletResponse
     * @param chain    - FilterChain
     * @throws IOException      -
     * @throws ServletException -
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.warn("In charset filter!");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        chain.doFilter(request, response);
    }
}

