package com.nextbook.controllers;

import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    @Inject
    private IUserProvider userProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or (isAnonymous() and #roleId <= 3)")
    public String addUser (@RequestParam("name") String name,
                           @RequestParam ("email") String email,
                           @RequestParam ("password") String pass,
                           @RequestParam (value = "active", required = false, defaultValue = "true") Boolean active,
                           @RequestParam ("roleId") int roleId,
                           Model model){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(md5PasswordEncoder.encodePassword(pass, null));
        if (active !=null)
            user.setActive(active);
        user.setRoleId(roleId);
        userProvider.addUser(user);
        return "redirect:/";
        //return "desktop";
    }

    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        if (sessionUtils.getCurrentUser().getId()!=id)
            userProvider.delete(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN') or (isAuthenticated() and #roleId <= 3)")
    public String updateUser(
            @RequestParam("name") String name,
            @RequestParam ("email") String email,
            @RequestParam ("password") String pass,
            @RequestParam (value = "active", required = false) Boolean active,
            @RequestParam ("roleId") int roleId,
            @PathVariable("id") int id) {
        User user = userProvider.getById(id);
        User current = sessionUtils.getCurrentUser();
        if (current.getRoleId() == 5 || user.getId()==current.getId()) {
            user.setName(name);
            user.setEmail(email);
            if (pass.length() != 0 && !pass.equals(user.getPassword()))
                user.setPassword(md5PasswordEncoder.encodePassword(pass, null));
            if (active != null)
                user.setActive(active);
            user.setRoleId(roleId);
            userProvider.update(user);
        }
        return "redirect:/";
        //return "users/update_profile";
    }

    @RequestMapping(value = {"/desktop", "/"})
    public String desktop() {
        return "desktop";
    }

    @RequestMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        User user = sessionUtils.getCurrentUser();
        if (user!=null) {
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", user.getRoleId());
        }
        return "users/profile";
    }

    @RequestMapping(value = "/profile/update")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Model model) {
        User user = sessionUtils.getCurrentUser();
        if (user!=null) {
            model.addAttribute("userId", user.getId());
            model.addAttribute("userEmail", user.getEmail());
            model.addAttribute("userName", user.getName());
            model.addAttribute("userRole", user.getRoleId());
        }
        return "users/update_profile";
    }

    @RequestMapping(value = "/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String users(Model model) {
        List<User> users = userProvider.getAll();
        model.addAttribute("users", users);
        return "users/users";
    }

    @RequestMapping(value = "/filters", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<User> filter(@RequestBody UserCriterion userCriterion,
                                           HttpServletResponse response){
        List<User> result = userProvider.getUsersByCriterion(userCriterion);
        response.setStatus(HttpServletResponse.SC_OK);
        if(result == null)
            result = new ArrayList<User>();
        return result;
    }
}
