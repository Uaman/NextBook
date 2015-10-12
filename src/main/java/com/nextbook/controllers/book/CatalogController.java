package com.nextbook.controllers.book;

import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.preview.CategoryPreview;
import com.nextbook.domain.preview.SubcategoryPreview;
import com.nextbook.services.ICategoryProvider;
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

    private List<CategoryPreview> categories;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allBooks(Model model, Locale locale) {
        List<BookPreview> books = new ArrayList<BookPreview>();
        for (Book book : bookProvider.getAllBooks())
            books.add(new BookPreview(book, locale));
        model.addAttribute("books", books);
        return "catalog/catalog";
    }

    //TODO: add links to database and get them from it
    @RequestMapping(value = "/{category_link}", method = RequestMethod.GET)
    public String categoryBooks(@PathVariable String category_link, Model model, Locale locale) {
        initCategoryList(locale);
        for (CategoryPreview category : categories)
            if (category.getLink().equals(category_link)) {
                BookCriterion bookCriterion = new BookCriterion();
                bookCriterion.setCategory(category.getId());
                model.addAttribute("books", getBooksByCriterion(bookCriterion, locale));
                break;
            }
        return "catalog/catalog";
    }

    //TODO: add links to database and get them from it
    @RequestMapping(value = "/{category_link}/{subCategory_link}", method = RequestMethod.GET)
    public String subCategoryBooks(@PathVariable String category_link,
                                   @PathVariable String subCategory_link, Model model, Locale locale) {
        initCategoryList(locale);
        boolean subCategoryFound = false;
        for (CategoryPreview category : categories)
            if (category.getLink().equals(category_link)) {
                for(SubcategoryPreview subCategory: category.getSubcategories()) {
                    if(subCategory.getLink().equals(subCategory_link)) {
                        BookCriterion bookCriterion = new BookCriterion();
                        bookCriterion.setSubCategory(subCategory.getId());
                        model.addAttribute("books", getBooksByCriterion(bookCriterion, locale));
                        subCategoryFound = true;
                        break;
                    }
                    if(subCategoryFound)
                        break;
                }
            }
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

    private List<BookPreview> getBooksByCriterion(BookCriterion bookCriterion, Locale locale) {
        List<BookPreview> result = new ArrayList<BookPreview>();
        for (Book b : bookProvider.getBooksByCriterion(bookCriterion))
            result.add(new BookPreview(b, locale));
        return result;
    }

}
