package com.nextbook.domain.request;

/**
 * Created by oleh on 24.10.2015.
 */
public class CreateCommentRequest{

    private String text;

    private int bookId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
