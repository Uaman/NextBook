package com.nextbook.services;

import com.nextbook.domain.criterion.CommentsCriterion;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.entities.UserEntity;
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

    CommentEntity getById(int id);

    CommentEntity update(CommentEntity comment);

    List<CommentEntity> userComments(UserEntity user);

    List<CommentEntity> bookComments(BookEntity book);

    boolean removeComment(CommentEntity comment);

    CommentEntity publisherActivateComment(CommentEntity comment);

    CommentEntity publisherDeactivateComment(CommentEntity comment);

    CommentEntity adminActivateComment(CommentEntity comment);

    CommentEntity adminDeactivateComment(CommentEntity comment);

    List<CommentEntity> getCommentsByCriterion(CommentsCriterion criterion);

}

