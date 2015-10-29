package com.nextbook.dao;

import com.nextbook.domain.entities.KeywordEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:25 PM
 */
public interface IKeywordDao {

    KeywordEntity getByName(String keyword);

    KeywordEntity update(KeywordEntity keyword);

    List<KeywordEntity> getListByKeyword(String keyword);
}
