package com.nextbook.dao;

import com.nextbook.domain.criterion.CommentsCriterion;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.entities.UserEntity;
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

    CommentEntity getById(int id);

    CommentEntity update(CommentEntity comment);

    List<CommentEntity> userComments(UserEntity user);

    List<CommentEntity> bookComments(BookEntity book);

    boolean removeComment(CommentEntity comment);

    List<CommentEntity> getCommentsByCriterion(CommentsCriterion criterion);

}
