package com.ben.StudentsBase.logging;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;


public class RequestLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        String message = super.createMessage(request, prefix, suffix);
        // Append useful info
        return message;
    }
}
