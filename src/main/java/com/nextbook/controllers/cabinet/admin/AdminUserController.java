package com.nextbook.controllers.cabinet.admin;

import com.nextbook.domain.filters.UserCriterion;
import com.nextbook.domain.forms.admin.AdminUserForm;
import com.nextbook.domain.forms.admin.UserEditForm;
import com.nextbook.domain.pojo.Role;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IRoleProvider;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SessionUtils sessionUtils;
    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;
    @Autowired
    private IRoleProvider roleProvider;

    /*
    @RequestMapping(value = "/add-user", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("@Secure.isAdmin()")
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
*/
    @RequestMapping(value = "/delete-user/{id}", method = RequestMethod.POST)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody boolean deleteUser(@PathVariable("id") int id) {
        if (sessionUtils.getCurrentUser().getId()!=id) {
            userProvider.delete(id);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody boolean updateUser(@RequestBody UserEditForm form){
        User user = userProvider.getById(form.getId());
        if (user != null) {
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            Role role = new Role();
            role.setId(form.getRoleId());
            user.setRole(role);
            user = userProvider.update(user);
            return user != null;
        }
        return false;
    }

    @RequestMapping(value = "/users-filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody List<User> filter(@RequestBody UserCriterion userCriterion,
                                           HttpServletResponse response){
        List<User> result = userProvider.getUsersByCriterion(userCriterion);
        response.setStatus(HttpServletResponse.SC_OK);
        if(result == null)
            result = new ArrayList<User>();
        return result;
    }

    @RequestMapping(value = "/all")
    @PreAuthorize("@Secure.isAdmin()")
    public String users(Model model) {
        model.addAttribute("users", userProvider.getAll());
        model.addAttribute("roles", roleProvider.getAll());
        return "admin/users/users";
    }

    /**
     *
     * @param userId - user id
     * @param status - user status
     * @return -1 - fail, 0 - status deactivated, 1 - status activated
     */
    @RequestMapping(value = "/change-active-user-status/{userId}/{status}", method = RequestMethod.POST)
    @PreAuthorize("@Secure.isAdmin()")
    public @ResponseBody int activateUser(@PathVariable("userId") int userId,
                                          @PathVariable("status") boolean status) {
        User user = userProvider.getById(userId);
        if(user == null)
            return -1;
        user.setActive(status);
        userProvider.update(user);
        return status ? 1 : 0;
    }

    @RequestMapping(value = "/edit-user", method = RequestMethod.GET)
    @PreAuthorize("@Secure.isAdmin()")
    public String editUser(@RequestParam("userId") int userId,
                           Model model){
        User user = userProvider.getById(userId);
        if(user == null)
            return "redirect:/admin/users/all";
        model.addAttribute("user", user);
        model.addAttribute("roles", roleProvider.getAll());
        return "admin/users/edit-user";
    }

}
