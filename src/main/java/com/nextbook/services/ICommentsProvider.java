package com.nextbook.services;

import com.nextbook.domain.criterion.CommentsCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.domain.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 1:14 AM
 */
@Service
public interface ICommentsProvider {

    Comment getById(int id);

    Comment update(Comment comment);

    List<Comment> userComments(User user);

    List<Comment> bookComments(Book book);

    boolean removeComment(Comment comment);

    Comment publisherActivateComment(Comment comment);

    Comment publisherDeactivateComment(Comment comment);

    Comment adminActivateComment(Comment comment);

    Comment adminDeactivateComment(Comment comment);

    List<Comment> getCommentsByCriterion(CommentsCriterion criterion);

}

