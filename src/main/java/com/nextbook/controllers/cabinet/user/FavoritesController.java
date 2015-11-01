package com.nextbook.controllers.cabinet.user;

import com.nextbook.domain.entities.FavoritesEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.services.IFavoritesProvider;
import com.nextbook.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Stacy on 10/12/15.
 */
@Controller
@RequestMapping("/user/favorites")
public class FavoritesController {
    @Autowired
    private IFavoritesProvider favoritesProvider;
    @Autowired
    private SessionUtils sessionUtils;

    @RequestMapping(value = "/")
    @PreAuthorize("isAuthenticated()")
    public String getFavorites(Model model,Locale locate) {
        UserEntity user = sessionUtils.getCurrentUser();
        if(user == null)
            return "redirect:/";
        List<FavoritesEntity> favoritesList = favoritesProvider.getAllFavorites(user);
        if(favoritesList == null)
            return "redirect:/cabinet/profile";
        List<BookPreview> bookPreviews = new ArrayList<BookPreview>();
        for(FavoritesEntity fav:favoritesList){
            bookPreviews.add(new BookPreview(fav.getBook(),locate));
        }
        model.addAttribute("favorites",bookPreviews);
        return "/users/favorites";
    }
    @RequestMapping(value="/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deletePublisher(@PathVariable int id) {
        UserEntity user = sessionUtils.getCurrentUser();
        if(user == null)
            return "redirect:/";
        favoritesProvider.deleteFromUserFavorites(user.getId(),id);
        return "redirect:/user/favorites/";
    }
}
