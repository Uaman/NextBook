package com.nextbook.controllers;

import com.nextbook.domain.pojo.User;
import com.nextbook.utils.SessionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    @Inject
    private SessionUtils sessionUtils;

    @RequestMapping(value = "/signin")
    public String login(Model model){
        return "auth/signin";
    }

    @RequestMapping(value="/signup")
    public String signUp(Model model) {
        return "auth/signup";
    }

    @RequestMapping(value = {"/desktop", "/"})
    public String desktop() {
        return "desktop";
    }

    @RequestMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        User user = sessionUtils.getCurrentUser();
        model.addAttribute("user", user);
        return "users/profile";
    }

    @RequestMapping(value = "/profile/update")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Model model) {
        User user = sessionUtils.getCurrentUser();
        model.addAttribute("user", user);
        return "users/update_profile";
    }

}
