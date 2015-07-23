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

/**
 * Created by Polomani on 21.07.2015.
 */
@Controller
@RequestMapping("/cabinet")
public class UsersController {

    @Inject
    private IUserProvider userProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;

    /*
    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAnonymous()")
    public String addUser (@RequestBody SimpleUserForm form){
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
        user.setActive(true);
        user.setRoleId(form.getRoleId());
        userProvider.addUser(user);
        return "redirect:/";
    }
    */
    @RequestMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        User user = sessionUtils.getCurrentUser();
        model.addAttribute("user", user);
        return "users/profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAuthenticated()")
    public String updateUser(@RequestBody SimpleUserForm form) {
        User user = sessionUtils.getCurrentUser();
        if (user != null) {
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            if (form.getPassword().length() != 0)
                user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
            //user.setRoleId(user.getRoleId()); //user can not change his role
            userProvider.update(user);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/edit")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Model model) {
        User user = sessionUtils.getCurrentUser();
        model.addAttribute("user", user);
        return "users/update_profile";
    }

}
