package com.nextbook.controllers.book;

import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.pojo.SubCategory;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.preview.CategoryPreview;
import com.nextbook.domain.request.CatalogRequest;
import com.nextbook.services.ICategoryProvider;
import com.nextbook.services.ISubCategoryProvider;
import com.nextbook.services.impl.BookProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by KutsykV on 11.10.2015.
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Inject
    private BookProvider bookProvider;
    @Inject
    private ICategoryProvider categoryProvider;
    @Inject
    private ISubCategoryProvider subCategoryProvider;

    private List<CategoryPreview> categories;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allBooks(Model model, Locale locale) {
        model.addAttribute("category", 0);
        model.addAttribute("subcategory", 0);
        model.addAttribute("last_book", 0);
        return "catalog/catalog";
    }

    @RequestMapping(value = "/{category_link}", method = RequestMethod.GET)
    public String categoryBooks(@PathVariable String category_link, Model model, Locale locale) {
        initCategoryList(locale);
        int categoryId = categoryProvider.getByLink(category_link).getId();
        model.addAttribute("category", categoryId);
        model.addAttribute("subcategory", 0);
        model.addAttribute("last_book", 0);
        return "catalog/catalog";
    }

    @RequestMapping(value = "/{category_link}/{subCategory_link}", method = RequestMethod.GET)
    public String subCategoryBooks(@PathVariable String category_link,
                                   @PathVariable String subCategory_link, Model model, Locale locale) {
        initCategoryList(locale);
        int subCategoryId = subCategoryProvider.getByLink(subCategory_link).getId();
        int categoryId = categoryProvider.getByLink(category_link).getId();
        model.addAttribute("category", categoryId);
        model.addAttribute("subcategory", subCategoryId);
        model.addAttribute("last_book", 0);
        return "catalog/catalog";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CategoryPreview> getCategories(Locale locale) {
        initCategoryList(locale);
        return categories;
    }

    private void initCategoryList(Locale locale) {
        categories = new ArrayList<CategoryPreview>();
        for (Category cat : categoryProvider.getAll())
            categories.add(new CategoryPreview(cat, locale));
    }

    @RequestMapping(value = "/getBooksByCriterion", method = RequestMethod.GET)
    public
    @ResponseBody
    List<BookPreview> getBooksByCriterion(@RequestParam(required = false) CatalogRequest catalogRequest, Locale locale) {
        List<Book> resultBooks = null;
        BookCriterion.Builder builder = new BookCriterion.Builder().status(Status.ACTIVE);
        if (catalogRequest == null)
            resultBooks = bookProvider.getBooksByCriterion(builder.build());
        else {
            if (catalogRequest.getSubCategory() > 0) {
                SubCategory subCategory = subCategoryProvider.getById(catalogRequest.getSubCategory());
                builder.subcategory(subCategory);
            }
            resultBooks = bookProvider.getBooksByCriterion(builder.build());
        }
        List<BookPreview> result = bookProvider.booksToBookPreviews(resultBooks, locale);
        return result;
    }

}