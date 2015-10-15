package com.nextbook.services;

import com.nextbook.domain.criterion.AuthorCriterion;
import com.nextbook.domain.pojo.Author;
import java.util.*;

/**
 * Created by Stacy on 7/24/15.
 */
public interface IAuthorProvider {

    Author updateAuthor(Author author);

    /**
     * All books connected with specific author need to be removed too, when this method is called?
     * @param authorId
     * @return
     */
    boolean deleteAuthor(int authorId);

    Author getById(int authorId);

    List<Author> getAll();

    List<Author> getFromMax(int from, int max);
    List<Author> getAuthorsByCriterion(AuthorCriterion criterion);

    Author getByFirstAndLastName(String fName, String lName);
}
