package com.nextbook.domain.criterion;

import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.UserEntity;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;
import com.nextbook.domain.filters.CommentsFilter;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/13/2015
 * Time: 11:07 PM
 */
public class CommentsCriterion {

    private Status status;

    private StatusChangedBy changedBy;

    private long timeFrom;

    private long timeTo;

    private BookEntity book;

    private UserEntity user;

    private int from;

    private int max;

    public CommentsCriterion(){}

    public CommentsCriterion(CommentsFilter filter){
        if(filter.getStatus() != null)      this.status = filter.getStatus();
        else                                this.status = Status.NOT_ACTIVE;

        if(filter.getChangedBy() != null)   this.changedBy = filter.getChangedBy();
        else                                this.changedBy = StatusChangedBy.NONE;

        this.timeFrom = filter.getTimeFrom();
        this.timeTo = filter.getTimeTo();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StatusChangedBy getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(StatusChangedBy changedBy) {
        this.changedBy = changedBy;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
