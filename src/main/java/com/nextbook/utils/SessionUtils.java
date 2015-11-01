package com.nextbook.utils;

import com.nextbook.domain.entities.UserEntity;
import com.nextbook.services.IUserProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Polomani on 16.07.2015.
 */
@Component
public class SessionUtils {

    @Inject
    private IUserProvider userProvider;

    public UserEntity getCurrentUser ()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            return userProvider.getUserByEmail(userDetails.getUsername());
        }
        return null;
    }
}
