package com.nextbook.controllers;

import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.forms.user.RegisterUserForm;
import com.nextbook.domain.pojo.*;
import com.nextbook.services.*;
import com.nextbook.utils.SessionUtils;
import com.nextbook.utils.StatisticUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Polomani on 09.07.2015.
 */

@Controller
public class IndexController {

    private int last_book = 0;
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

    @RequestMapping(value = "/signin")
    public String login(Model model) {
        return "auth/signin";
    }

    @RequestMapping(value = "/signup")
    public String signUp(Model model) {
        return "auth/signup";
    }

    @RequestMapping(value = {"/"})
    public String desktop(Model model, Locale locale) {
//        int booksQuantity = bookProvider.getBooksQuantity();
//        int from = Math.max(0, booksQuantity - BOOKS_ON_PAGE);
//        BookCriterion bookCriterion = new BookCriterion();
//        bookCriterion.setFrom(from);
//        bookCriterion.setMax(BOOKS_ON_PAGE);
//        List<Book> books = bookProvider.getBooksByCriterion(bookCriterion);
//        List<BookPreview> lastBooks = new ArrayList<BookPreview>();
//        for (Book b:books)
//            lastBooks.add(new BookPreview(b, locale));
//        List<Category> categories = categoryProvider.getAll();
//        List<CategoryPreview> respCategories = new ArrayList<CategoryPreview>();
//        for (Category c:categories)
//            respCategories.add(new CategoryPreview(c, locale));
//        model.addAttribute("categories", categoryProvider.getAll());
//        model.addAttribute("lastBooks", lastBooks);
//        model.addAttribute("booksQuantity", booksQuantity);
//        model.addAttribute("publishersQuantity", publisherProvider.getPublishersQuantity());
        BookCriterion bookCriterionForm = new BookCriterion();
        model.addAttribute("bookCriterion", bookCriterionForm);
        model.addAttribute("categories", categoryProvider.getAll());
        model.addAttribute("last_book", last_book);
        return "main/index";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Category> categories() {
        return categoryProvider.getAll();
    }

    @RequestMapping(value = "/subcategories", method = RequestMethod.GET)
    public
    @ResponseBody
    List<SubCategory> subcategories(
            @RequestParam(value = "category", required = false) Integer category) {
        if(category == null)
            return subCategoryProvider.getAll();
        return subCategoryProvider.getAllByCategoryId(category);
    }

    @RequestMapping(value = "/getbooks", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<Book> getBooks(@RequestParam(value = "category", required = false) Integer category,
                       @RequestParam(value = "subcategory", required = false) Integer subcategory) {
        Set<Book> result = new HashSet<Book>();
        if (category != null && subcategory == null) {
            for (SubCategory subCategory : subCategoryProvider.getAllByCategoryId(category)) {
                BookCriterion criterion = new BookCriterion();
                criterion.setSubCategory(subCategory.getId());
                result.addAll(bookProvider.getBooksByCriterion(criterion));
            }
        } else if (subcategory != null) {
            BookCriterion criterion = new BookCriterion();
            criterion.setSubCategory(subcategory);
            result.addAll(bookProvider.getBooksByCriterion(criterion));
        } else
            result.addAll(bookProvider.getAllBooks());
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
    @PreAuthorize("isAnonymous()")
    public @ResponseBody boolean addUser(@RequestBody RegisterUserForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(md5PasswordEncoder.encodePassword(form.getPassword(), null));
        user.setActive(true);
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user = userProvider.update(user);
        statisticUtil.registrationEvent(user);
        return user != null;
    }
}
