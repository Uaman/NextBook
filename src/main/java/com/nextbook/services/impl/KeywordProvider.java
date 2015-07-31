package com.nextbook.services.impl;

import com.nextbook.dao.IKeywordDao;
import com.nextbook.dao.impl.KeywordDAO;
import com.nextbook.domain.pojo.Keyword;
import com.nextbook.services.IKeywordProvider;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:24 PM
 */
@Service
public class KeywordProvider implements IKeywordProvider {

    private IKeywordDao keywordDao = new KeywordDAO();

    @Override
    public Keyword getByName(String keyword) {
        if(keyword == null)
            return null;
        return keywordDao.getByName(keyword);
    }

    @Override
    public Keyword update(Keyword keyword) {
        if(keyword == null)
            return null;
        return keywordDao.update(keyword);
    }

}
