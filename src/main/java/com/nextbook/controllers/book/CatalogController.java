package com.nextbook.controllers.book;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.preview.BookPreview;
import com.nextbook.domain.preview.CategoryPreview;
import com.nextbook.services.ICategoryProvider;
import com.nextbook.services.impl.BookProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allBooks(Model model,Locale locale){
        List<BookPreview> books = new ArrayList<BookPreview>();
        for(Book book: bookProvider.getAllBooks())
            books.add(new BookPreview(book, locale));
        model.addAttribute("books", books);
        return "catalog/catalog";
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public @ResponseBody List<CategoryPreview> getCategories(Locale locale){
        List<CategoryPreview> categories = new ArrayList<CategoryPreview>();
        for(Category cat: categoryProvider.getAll())
            categories.add(new CategoryPreview(cat, locale));
        return categories;
    }
}
