package com.nextbook.services;

import com.nextbook.domain.pojo.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:24 PM
 */
@Service
public interface IKeywordProvider {

    Keyword getByName(String keyword);

    Keyword update(Keyword keyword);

    List<Keyword> getListByKeyword(String keyword);

}
