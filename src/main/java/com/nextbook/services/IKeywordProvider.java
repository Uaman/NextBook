package com.nextbook.services;

import com.nextbook.domain.entities.KeywordEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/27/2015
 * Time: 7:24 PM
 */
public interface IKeywordProvider {

    KeywordEntity getByName(String keyword);

    KeywordEntity update(KeywordEntity keyword);

    List<KeywordEntity> getListByKeyword(String keyword);

}
