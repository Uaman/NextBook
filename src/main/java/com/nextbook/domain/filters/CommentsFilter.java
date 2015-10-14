package com.nextbook.domain.filters;

import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/14/2015
 * Time: 9:23 AM
 */
public class CommentsFilter {

    private Status status;

    private StatusChangedBy changedBy;

    private long timeFrom;

    private long timeTo;

    private int bookId;

    private int userId;

    private int page;

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
