package com.nextbook.dao;

import com.nextbook.domain.pojo.Author;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/24/2015
 * Time: 4:08 PM
 */
public interface IAuthorDao {

    Author updateAuthor(Author author);

    /**
     * All books connected with specific author need to be removed too, when this method is called?
     * @param authorId
     * @return
     */
    boolean deleteAuthor(int authorId);

    Author getById(int authorId);

    List<Author> getFromMax(int from, int max);

}
