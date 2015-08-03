package com.nextbook.controllers;

import com.nextbook.domain.forms.user.RegisterUserForm;
import com.nextbook.domain.pojo.Role;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    public @ResponseBody boolean addUser(@RequestBody RegisterUserForm form){
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
        user.setActive(true);
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user = userProvider.update(user);
        return user != null;
    }
}
