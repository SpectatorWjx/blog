package com.wang.blog.config.shiro;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AbsoluteSendRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RedirectResponseWrapper redirectResponseWrapper = new RedirectResponseWrapper(request, response);
        filterChain.doFilter(request, redirectResponseWrapper);
    }

}