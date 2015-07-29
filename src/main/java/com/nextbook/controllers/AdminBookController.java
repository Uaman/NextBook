package com.nextbook.controllers;

import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.info.BookMainInfo;
import com.nextbook.domain.pojo.Book;
import com.nextbook.services.IBookProvider;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Polomani on 27.07.2015.
 */
@Controller
@RequestMapping("/admin/book")
public class AdminBookController {

    @Inject
    IBookProvider bookProvider;

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping (value = "/filter", method = RequestMethod.POST, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody List<BookMainInfo> getBooksByCriterion(@RequestBody BookCriterion criterion) {
        List<Book> books = bookProvider.getBooksByCriterion(criterion);
        List<BookMainInfo> res = new ArrayList<BookMainInfo>();
        if (books!=null)
            for (Book book: books)
                res.add(new BookMainInfo(book));
        return res;
    }

}
