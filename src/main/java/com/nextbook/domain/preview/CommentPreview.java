package com.nextbook.domain.preview;

import com.nextbook.domain.pojo.Comment;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/13/2015
 * Time: 7:18 PM
 */
public class CommentPreview {

    private int id;

    private String user;

    private String comment;

    private long time;

    public CommentPreview(){}

    public CommentPreview(Comment comment){
        this.id = comment.getId();
        this.user = comment.getUser().getName();
        this.comment = comment.getComment();
        this.time = comment.getTime();
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
