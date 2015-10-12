package com.nextbook.services.impl;

import com.nextbook.dao.ICommentsDAO;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.domain.pojo.User;
import com.nextbook.services.ICommentsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:14 AM
 */
@Service
public class CommentsProvider implements ICommentsProvider {

    @Autowired
    private ICommentsDAO commentsDAO;

    @Override
    public Comment getById(int id) {
        return commentsDAO.getById(id);
    }

    @Override
    public Comment update(Comment comment) {
        if(comment == null)
            return null;
        return commentsDAO.update(comment);
    }

    @Override
    public List<Comment> userComments(User user) {
        if(user == null)
            return null;
        return commentsDAO.userComments(user);
    }

    @Override
    public List<Comment> bookComments(Book book) {
        if(book == null)
            return null;
        return commentsDAO.bookComments(book);
    }

    @Override
    public boolean removeComment(Comment comment) {
        if(comment == null)
            return false;
        return commentsDAO.removeComment(comment);
    }


}
