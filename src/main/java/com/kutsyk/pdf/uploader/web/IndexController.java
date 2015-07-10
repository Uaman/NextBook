package com.kutsyk.pdf.uploader.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    @RequestMapping(value = "/user/add")
    public String addUser() {
        return "desktop";
    }

    @RequestMapping(value = "/user/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        return "desktop";
    }

    @RequestMapping(value = "/user/{id}/update")
    public String updateUser(@PathVariable("id") long id) {
        return "update_profile";
    }

    @RequestMapping(value = "/user/{id}/ban")
    public String banUser(@PathVariable("id") long id) {
        return "desktop";
    }

    @RequestMapping(value = "/user/{id}")
    public String getUser(@PathVariable("id") long id) {
        return "profile";
    }

    @RequestMapping(value = "/desktop")
    public String desktop() {
        return "desktop";
    }

    @RequestMapping(value = "/profile")
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile/update")
    public String editProfile() {
        return "update_profile";
    }

    @RequestMapping(value = "/users")
    public String users() {
        return "users";
    }
}
