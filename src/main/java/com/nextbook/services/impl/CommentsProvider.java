package com.nextbook.services.impl;

import com.nextbook.dao.ICommentsDAO;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.CommentEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;
import com.nextbook.domain.criterion.CommentsCriterion;
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
    public CommentEntity getById(int id) {
        return commentsDAO.getById(id);
    }

    @Override
    public CommentEntity update(CommentEntity comment) {
        if(comment == null)
            return null;
        return commentsDAO.update(comment);
    }

    @Override
    public List<CommentEntity> userComments(UserEntity user) {
        if(user == null)
            return null;
        return commentsDAO.userComments(user);
    }

    @Override
    public List<CommentEntity> bookComments(BookEntity book) {
        if(book == null)
            return null;
        return commentsDAO.bookComments(book);
    }

    @Override
    public boolean removeComment(CommentEntity comment) {
        if(comment == null)
            return false;
        return commentsDAO.removeComment(comment);
    }

    @Override
    public CommentEntity publisherActivateComment(CommentEntity comment) {
        if(comment == null)
            return null;
        comment.setStatus(Status.ACTIVE);
        comment.setChangedBy(StatusChangedBy.PUBLISHER);
        return commentsDAO.update(comment);
    }

    @Override
    public CommentEntity publisherDeactivateComment(CommentEntity comment) {
        if(comment == null)
            return null;
        comment.setStatus(Status.NOT_ACTIVE);
        comment.setChangedBy(StatusChangedBy.PUBLISHER);
        return commentsDAO.update(comment);
    }

    @Override
    public CommentEntity adminActivateComment(CommentEntity comment) {
        if(comment == null)
            return null;
        comment.setStatus(Status.ACTIVE);
        comment.setChangedBy(StatusChangedBy.ADMIN);
        return commentsDAO.update(comment);
    }

    @Override
    public CommentEntity adminDeactivateComment(CommentEntity comment) {
        if(comment == null)
            return null;
        comment.setStatus(Status.NOT_ACTIVE);
        comment.setChangedBy(StatusChangedBy.ADMIN);
        return commentsDAO.update(comment);
    }

    @Override
    public List<CommentEntity> getCommentsByCriterion(CommentsCriterion criterion) {
        if(criterion == null)
            return null;
        return commentsDAO.getCommentsByCriterion(criterion);
    }


}
