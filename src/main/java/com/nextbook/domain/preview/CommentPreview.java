package com.nextbook.domain.preview;

import com.nextbook.domain.enums.Status;
import com.nextbook.domain.pojo.Comment;
import com.nextbook.domain.pojo.User;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/13/2015
 * Time: 7:18 PM
 */
public class CommentPreview {

    private int id;

    private User user;

    private String comment;

    private long time;

    private Status status;

    public CommentPreview(){}

    public CommentPreview(Comment comment){
        this.id = comment.getId();
        this.user = comment.getUser();
        this.comment = comment.getComment();
        this.time = comment.getTime();
        this.status = comment.getStatus();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
