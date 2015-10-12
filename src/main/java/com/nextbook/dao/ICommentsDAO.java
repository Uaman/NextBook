package com.nextbook.dao;

import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:02 AM
 */
@Repository
public interface ICommentsDAO {

    Comment getById(int id);

    Comment update(Comment comment);

    List<Comment> userComments(User user);

    List<Comment> bookComments(Book book);

    boolean removeComment(Comment comment);

}
