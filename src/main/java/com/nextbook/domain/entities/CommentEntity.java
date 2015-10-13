package com.nextbook.domain.entities;

import com.nextbook.domain.enums.Status;
import com.nextbook.domain.enums.StatusChangedBy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/12/2015
 * Time: 12:52 AM
 */

@Entity
@Table(name = "comments")
@NamedQueries({
        @NamedQuery(name = CommentEntity.GET_ALL_COMMENTS_FOR_USER, query = "SELECT comment FROM CommentEntity comment WHERE comment.user.id = :user_id"),
        @NamedQuery(name = CommentEntity.GET_ALL_COMMENTS_FOR_BOOK, query = "SELECT comment FROM CommentEntity comment WHERE comment.book.id = :book_id")
})
public class CommentEntity {

    public static final String GET_ALL_COMMENTS_FOR_USER = "CommentEntity.getAllCommentsForUser";
    public static final String GET_ALL_COMMENTS_FOR_BOOK = "CommentEntity.getAllCommentsForBook";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName="ID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName="ID")
    private UserEntity user;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_TIME")
    private long time;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHANGED_BY")
    private StatusChangedBy changedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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


    public StatusChangedBy getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(StatusChangedBy changedBy) {
        this.changedBy = changedBy;
    }
}
