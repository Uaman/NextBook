package com.nextbook.controllers;

import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.IUserProvider;
import com.nextbook.services.impl.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/14/2015
 * Time: 12:02 PM
 */

@Controller
public class TestAdminTableController {

    private IUserProvider userProvider = new UserProvider();

    @RequestMapping("/")
    public String index(Model model){
        List<User> users = userProvider.getAll();
        model.addAttribute("users", users);
        return "test";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        try {
            int userId = Integer.valueOf(request.getParameter("id"));
            User user = userProvider.getById(userId);
            user.setActive(Boolean.valueOf(request.getParameter("active")));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setName(request.getParameter("name"));
            user.setRoleId(Integer.valueOf(request.getParameter("roleId")));
            user = userProvider.update(user);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setActive(Boolean.valueOf(request.getParameter("active")));
        user.setRoleId(Integer.valueOf(request.getParameter("roleId")));
        boolean status = userProvider.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request){
        try{
            int userId = Integer.valueOf(request.getParameter("id"));
            if(userId >= 0){
                boolean status = userProvider.delete(userId);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }

}
