package com.nextbook.dao;

import com.nextbook.domain.pojo.Keyword;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:25 PM
 */
@Repository
public interface IKeywordDao {

    Keyword getByName(String keyword);

    Keyword update(Keyword keyword);

    List<Keyword> getListByKeyword(String keyword);
}
