package com.nextbook.utils;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;

/**
 * Created by Polomani on 09.08.2015.
 */
public class CustomWebSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler{

    @Override
    protected SecurityExpressionRoot createSecurityExpressionRoot(
            Authentication authentication, FilterInvocation fi) {
        return new CustomWebSecurityExpressionRoot(authentication, fi);
    }

}
