package com.nextbook.dao;

import com.nextbook.domain.pojo.Keyword;
import org.springframework.stereotype.Repository;

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

}
