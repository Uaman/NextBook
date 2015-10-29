package com.nextbook.services.impl;

import com.nextbook.dao.IKeywordDao;
import com.nextbook.domain.entities.KeywordEntity;
import com.nextbook.services.IKeywordProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:24 PM
 */
@Service
public class KeywordProvider implements IKeywordProvider {

    @Autowired
    private IKeywordDao keywordDao;

    @Override
    public KeywordEntity getByName(String keyword) {
        if(keyword == null)
            return null;
        return keywordDao.getByName(keyword);
    }

    @Override
    public KeywordEntity update(KeywordEntity keyword) {
        if(keyword == null)
            return null;
        return keywordDao.update(keyword);
    }

    @Override
    public List<KeywordEntity> getListByKeyword(String keyword) {
        if(keyword == null || keyword.equals(""))
            return null;
        return keywordDao.getListByKeyword(keyword);
    }


}
