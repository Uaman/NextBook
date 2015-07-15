package com.nextbook.controllers;

import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
    IUserProvider userProvider;

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String addUser (@RequestParam("name") String name,
                @RequestParam ("email") String email,
                @RequestParam ("password") String pass,
                @RequestParam (value = "active", required = false) Boolean active,
                @RequestParam ("roleId") int roleId,
                Model model){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pass);
        if (active !=null)
            user.setActive(active);
        user.setRoleId(roleId);
        userProvider.addUser(user);
        return "redirect:/users";
        //return "desktop";
    }

    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int id) {
        userProvider.delete(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.POST)
    public String updateUser(
            @RequestParam("name") String name,
            @RequestParam ("email") String email,
            @RequestParam ("password") String pass,
            @RequestParam ("active") Boolean active,
            @RequestParam ("roleId") int roleId,
            @PathVariable("id") int id) {

        User user = userProvider.getById(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pass);
        user.setActive(active);
        user.setRoleId(roleId);
        userProvider.update(user);
        return "redirect:/users";
        //return "users/update_profile";
    }

    @RequestMapping(value = "/user/{id}/ban")
    public String banUser(@PathVariable("id") long id) {
        return "desktop";
    }

    @RequestMapping(value = "/user/{id}")
    public String getUser(@PathVariable("id") long id) {
        return "users/profile";
    }

    @RequestMapping(value = {"/desktop", "/"})
    public String desktop() {
        return "desktop";
    }

    @RequestMapping(value = "/profile")
    public String profile(Model model) {
        UserDetails userDetails;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) auth.getPrincipal();
            model.addAttribute("userName", userDetails.getUsername());
        }

        return "users/profile";
    }

    @RequestMapping(value = "/profile/update")
    public String editProfile() {
        return "users/update_profile";
    }

    @RequestMapping(value = "/users")
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
