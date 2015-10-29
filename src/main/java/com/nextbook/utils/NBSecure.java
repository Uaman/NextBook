package com.nextbook.utils;

import com.nextbook.domain.entities.PermissionEntity;
import com.nextbook.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

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
        UserEntity user = sessionUtils.getCurrentUser();
        if (user==null) return false;
        for (PermissionEntity p :user.getRoleEntity().getPermissions()) {
            if (p.getName().equals(s))
                return true;
        }
        return false;
    }
}
