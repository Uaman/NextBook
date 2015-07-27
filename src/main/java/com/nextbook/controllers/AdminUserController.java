package com.nextbook.controllers;

import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.forms.AdminUserForm;
import com.nextbook.domain.pojo.Role;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 21.07.2015.
 */
@Controller
@RequestMapping ("/admin/users")
public class AdminUserController {

    @Inject
    private IUserProvider userProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;

    @RequestMapping(value = "/add-user", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUser (@RequestBody AdminUserForm form){
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
        user.setActive(form.isActive());
        Role role = new Role();
        role.setId(form.getRoleId());
        user.setRole(role);
        userProvider.update(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete-user/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") int id) {
        if (sessionUtils.getCurrentUser().getId()!=id)
            userProvider.delete(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@RequestBody AdminUserForm form){
        User user = userProvider.getById(form.getId());
        if (user != null) {
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            if (!form.getPassword().equals(user.getPassword()))
                user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
            user.setActive(form.isActive());
            Role role = new Role();
            role.setId(form.getId());
            user.setRole(role);
            userProvider.update(user);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/users-filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody
    List<User> filter(@RequestBody UserCriterion userCriterion,
                      HttpServletResponse response){
        List<User> result = userProvider.getUsersByCriterion(userCriterion);
        response.setStatus(HttpServletResponse.SC_OK);
        if(result == null)
            result = new ArrayList<User>();
        return result;
    }

    @RequestMapping(value = "/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String users(Model model) {
        List<User> users = userProvider.getAll();
        model.addAttribute("users", users);
        return "users/users";
    }

}
