package com.nextbook.controllers;

import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.exceptions.EmailAlreadyExistsException;
import com.nextbook.domain.forms.user.RegisterUserForm;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.response.ResponseOnAjaxRegistration;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import com.nextbook.utils.StatisticUtil;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    private int BOOK_ON_PAGE = 10;
    @Inject
    private ICategoryProvider categoryProvider;
    @Inject
    private ISubCategoryProvider subCategoryProvider;
    @Inject
    private SessionUtils sessionUtils;
    @Inject
    private IUserProvider userProvider;
    @Inject
    private IBookProvider bookProvider;
    @Inject
    private IPublisherProvider publisherProvider;
    @Inject
    private StatisticUtil statisticUtil;
    @Inject
    private Md5PasswordEncoder md5PasswordEncoder;
    @Inject
    private Validator validator;
    @Inject
    private MessageSource messageSource;

    @RequestMapping(value = {"/"})
    public String desktop(Model model, Locale locale) {
        int booksQuantity = bookProvider.getCountByCriterion(new BookCriterion.Builder().status(Status.ACTIVE).build());
        int from = Math.max(0, booksQuantity - BOOK_ON_PAGE);
        BookCriterion bookCriterion = new BookCriterion.Builder()
                .from(from)
                .max(BOOK_ON_PAGE)
                .status(Status.ACTIVE)
                .build();

        List<BookEntity> books = bookProvider.getBooksByCriterion(bookCriterion);
        List<BookPreview> lastBooks = bookProvider.booksToBookPreviews(books, locale);
        model.addAttribute("last_books", lastBooks);
        model.addAttribute("numberOfLastBooks", lastBooks.size());
        return "main/index";
    }

    @RequestMapping(value = "/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        try {
            String referer = request.getHeader("Referer");
            if (referer.indexOf("?")==-1)
                response.sendRedirect(referer+"?loginerror=true");
            else if (!referer.contains("loginerror=true"))
                response.sendRedirect(referer+"&loginerror=true");
            else
                response.sendRedirect(referer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login/success")
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        try {
            String referer = request.getHeader("Referer");
            referer = referer.replace("loginerror=true&", "");
            referer = referer.replace("?loginerror=true", "");
            referer = referer.replace("&loginerror=true", "");
            response.sendRedirect(referer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/signup")
    public String signUp(Model model) {
        if (sessionUtils.getCurrentUser()==null) {
            model.addAttribute("maxEmailLength", RegisterUserForm.MAX_EMAIL_LENGTH);
            model.addAttribute("maxNameLength", RegisterUserForm.MAX_NAME_LENGTH);
            model.addAttribute("minNameLength", RegisterUserForm.MIN_NAME_LENGTH);
            model.addAttribute("maxPasswordLength", RegisterUserForm.MAX_PASSWORD_LENGTH);
            model.addAttribute("minPasswordLength", RegisterUserForm.MIN_PASSWORD_LENGTH);
            return "auth/signup";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAnonymous()")
    public @ResponseBody ResponseOnAjaxRegistration addUser(@RequestBody RegisterUserForm form, Locale locale) {
        ResponseOnAjaxRegistration<RegisterUserForm> response = new ResponseOnAjaxRegistration<RegisterUserForm>(validator.validate(form), messageSource, locale);
        if(!response.hasErrors()) {
            UserEntity user = new UserEntity();
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
            user.setActive(true);

            try {
                user = userProvider.registerNewUser(user);
                if(user == null){
                    response.setCode(ResponseOnAjaxRegistration.PROBLEMS_WITH_SERVICE);
                } else {
                    statisticUtil.registrationEvent(user);
                }
                response.setCode(ResponseOnAjaxRegistration.OK);
            } catch (EmailAlreadyExistsException e) {
                response.addError(messageSource.getMessage("sign.up.error.email.already.exists", null, locale));
            }
        } else {
            response.setCode(ResponseOnAjaxRegistration.PROBLEMS_WITH_VALIDATION);
        }
        return response;
    }

}
