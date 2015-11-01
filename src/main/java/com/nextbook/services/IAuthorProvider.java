package com.nextbook.services;

import com.nextbook.domain.criterion.AuthorCriterion;
import com.nextbook.domain.entities.AuthorEntity;
import java.util.*;

/**
 * Created by Stacy on 7/24/15.
 */
public interface IAuthorProvider {

    AuthorEntity updateAuthor(AuthorEntity author);

    /**
     * All books connected with specific author need to be removed too, when this method is called?
     * @param authorId
     * @return
     */
    boolean deleteAuthor(int authorId);

    AuthorEntity getById(int authorId);

    List<AuthorEntity> getAll();

    List<AuthorEntity> getFromMax(int from, int max);
    List<AuthorEntity> getAuthorsByCriterion(AuthorCriterion criterion);

    AuthorEntity getByFirstAndLastName(String fName, String lName);
}
