package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.entities.PublisherEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.forms.user.UserChangeNameEmail;
import com.nextbook.domain.forms.user.UserChangePassword;
import com.nextbook.services.IFavoritesProvider;
import com.nextbook.services.IPublisherProvider;
import com.nextbook.services.IUserProvider;
import com.nextbook.utils.StatisticUtil;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    @Inject
    private IPublisherProvider publisherProvider;
    @Inject
    private StatisticUtil statisticUtil;
    @Autowired
    private IFavoritesProvider favoritesProvider;

    @RequestMapping(value = "/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model) {
        UserEntity user = sessionUtils.getCurrentUser();
        model.addAttribute("user", user);
        PublisherEntity publisher = publisherProvider.getPublisherByUser(user);
        model.addAttribute("publisher", publisher);
        model.addAttribute("hasFavorites",favoritesProvider.hasFavorites(user));
        return "users/profile";
    }

    @RequestMapping(value = "/edit-profile")
    @PreAuthorize("isAuthenticated()")
    public String editProfile(Model model) {
        UserEntity user = sessionUtils.getCurrentUser();
        if(user == null)
            return "redirect:/";
        model.addAttribute("user", user);
        return "users/update_profile";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody boolean updateUser(@RequestBody UserChangeNameEmail form) {
        UserEntity user = sessionUtils.getCurrentUser();
        if (user != null) {
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            userProvider.update(user);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAuthenticated()")
    public @ResponseBody List<String> changePassword(@RequestBody UserChangePassword form) {
        List<String> response = new ArrayList<String>();
        UserEntity user = sessionUtils.getCurrentUser();
        if(user != null){
            if(user.getPassword().equals(md5PasswordEncoder.encodePassword(form.getOldPassword(), null))){
                user.setPassword(md5PasswordEncoder.encodePassword(form.getNewPassword(), null));
                userProvider.update(user);
                response.add("saved");
            } else {
                response.add("old password incorrect");
            }
        } else {
            response.add("Fail");
        }

        return response;
    }

}
