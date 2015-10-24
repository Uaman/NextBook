package com.nextbook.controllers.book;

import com.nextbook.domain.entities.BookEntity;
import com.nextbook.services.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by KutsykV on 23.10.2015.
 */
@Controller
public class SearchController {

    @Inject
    private ISearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody
    Set<BookEntity> addUser(@RequestParam(value = "q") String query) {
        return searchService.find(query, 15, 0);
    }

}
