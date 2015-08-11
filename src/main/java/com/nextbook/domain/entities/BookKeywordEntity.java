package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/10/2015
 * Time: 6:52 PM
 */
@Entity
@Table(name = "keywords_to_book")
@NamedQueries({
    @NamedQuery(name = BookKeywordEntity.getByBookAndKeywordIds, query = "SELECT bookKeyword FROM BookKeywordEntity bookKeyword WHERE bookKeyword.book.id=:bookId AND bookKeyword.keyword.id=:keywordId")
})
public class BookKeywordEntity {

    public static final String getByBookAndKeywordIds = "getByBookAndKeywordIds";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName="ID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "KEYWORD_ID", referencedColumnName="ID")
    private KeywordEntity keyword;

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

    public KeywordEntity getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordEntity keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookKeywordEntity that = (BookKeywordEntity) o;

        if (id != that.id) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        if (keyword != null ? !keyword.equals(that.keyword) : that.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }
}
