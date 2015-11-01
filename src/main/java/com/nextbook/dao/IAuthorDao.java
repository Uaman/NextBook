package com.nextbook.dao;

import com.nextbook.domain.criterion.AuthorCriterion;
import com.nextbook.domain.entities.AuthorEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/24/2015
 * Time: 4:08 PM
 */
public interface IAuthorDao {

    AuthorEntity updateAuthor(AuthorEntity author);

    /**
     * All books connected with specific author need to be removed too, when this method is called?
     * @param authorId
     * @return
     */
    boolean deleteAuthor(int authorId);

    AuthorEntity getById(int authorId);

    List<AuthorEntity> getFromMax(int from, int max);

    List<AuthorEntity> getAuthorsByCriterion(AuthorCriterion criterion);

    AuthorEntity getByFirstAndLastName(String fName, String lName);
}
