package com.nextbook.utils;

import com.nextbook.domain.pojo.Permission;
import com.nextbook.domain.pojo.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by Polomani on 09.08.2015.
 */

@Component(value = "Secure")
public class NBSecure {

    @Inject
    SessionUtils sessionUtils;

    public boolean isUser() {
        return hasRole("ROLE_USER");
    }

    public boolean isAuthor() {
        return hasRole("ROLE_AUTHOR");
    }

    public boolean isPublisher() {
        return hasRole("ROLE_PUBLISHER");
    }

    public boolean isModer() {
        return hasRole("ROLE_MODERATOR");
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public boolean hasRole (String s) {
        User user = sessionUtils.getCurrentUser();
        if (user==null) return false;
        for (Permission p :user.getRole().getPermissions()) {
            if (p.getName().equals(s))
                return true;
        }
        return false;
    }
}
