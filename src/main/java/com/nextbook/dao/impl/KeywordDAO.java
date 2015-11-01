package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IKeywordDao;
import com.nextbook.domain.entities.KeywordEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:26 PM
 */
@Service
@Transactional
public class KeywordDAO implements IKeywordDao{

    @Inject
    private Dao baseDao;

    @Transactional
    public KeywordEntity getByName(final String keyword) {
        List<KeywordEntity> result =
                baseDao.executeNamedQueryWithParams(
                        KeywordEntity.class,
                        KeywordEntity.getByKeyword,
                        new HashMap<String, Object>(){{
                            put("keyword", keyword);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }

    @Transactional
    public KeywordEntity update(KeywordEntity keyword) {
        return baseDao.attachWithMerge(keyword);
    }

    @Transactional
    public List<KeywordEntity> getListByKeyword(final String keyword) {
        List<KeywordEntity> result =
                baseDao.executeNamedQueryWithParams(
                        KeywordEntity.class,
                        KeywordEntity.getKeywordsByPart,
                        new HashMap<String, Object>(){{
                            put("keyword", '%'+keyword+'%');
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

}
