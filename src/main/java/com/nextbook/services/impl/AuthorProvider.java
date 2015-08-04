package com.nextbook.services.impl;

import com.nextbook.dao.IAuthorDao;
import com.nextbook.dao.impl.AuthorDao;
import com.nextbook.domain.filters.AuthorCriterion;
import com.nextbook.domain.pojo.Author;
import com.nextbook.services.IAuthorProvider;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/24/2015
 * Time: 3:49 PM
 */
@Service
public class AuthorProvider implements IAuthorProvider{

    @Inject
    private IAuthorDao authorDao;

    @Override
    public Author updateAuthor(Author author) {
        if(author == null)
            return null;
        return authorDao.updateAuthor(author);
    }

    @Override
    public boolean deleteAuthor(int authorId) {
        return authorDao.deleteAuthor(authorId);
    }

    @Override
    public Author getById(int authorId) {
        return authorDao.getById(authorId);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getFromMax(0, 0);
    }

    @Override
    public List<Author> getFromMax(int from, int max) {
        return authorDao.getFromMax(from, max);
    }
    @Override
    public List<Author> getAuthorsByCriterion(AuthorCriterion criterion) {
        return authorDao.getAuthorsByCriterion(criterion);
    }

    @Override
    public Author getByFirstAndLastName(String fName, String lName) {
        if(!validString(fName) || !validString(lName))
            return  null;
        return authorDao.getByFirstAndLastName(fName, lName);
    }

    private boolean validString(String s){
        return s != null && !s.equals("");
    }
}
