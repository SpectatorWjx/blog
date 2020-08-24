package com.wang.blog.config.shiro;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RedirectResponseWrapper extends HttpServletResponseWrapper {

    private final HttpServletRequest request;

    public RedirectResponseWrapper(final HttpServletRequest inRequest, final HttpServletResponse response) {
        super(response);
        this.request = inRequest;
    }

    @Override
    public void sendRedirect(final String pLocation) throws IOException {

        if (StringUtils.isBlank(pLocation)) {
            super.sendRedirect(pLocation);
            return;
        }

        try {
            final URI uri = new URI(pLocation);
            if (uri.getScheme() != null) {
                super.sendRedirect(pLocation);
                return;
            }
        } catch (URISyntaxException ex) {
            super.sendRedirect(pLocation);
        }

        // !!! FIX Scheme  !!!
        String finalurl = "https://" + this.request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            finalurl += ":" + request.getServerPort();
        }
        finalurl += pLocation;
        super.sendRedirect(finalurl);
    }

}