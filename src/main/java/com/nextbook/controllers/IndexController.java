package com.nextbook.controllers;

import com.nextbook.domain.forms.SimpleUserForm;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    @Inject
    private SessionUtils sessionUtils;

    @Inject
    private IUserProvider userProvider;

    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;

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
        return "main/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAnonymous()")
    public @ResponseBody boolean addUser(@RequestBody SimpleUserForm form){
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
        user.setActive(true);
        user.setRoleId(form.getRoleId());
        boolean added = userProvider.addUser(user);
        return added;
    }
/*
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
*/
}
