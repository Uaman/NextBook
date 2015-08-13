package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/13/2015
 * Time: 10:58 AM
 */

@Entity
@Table(name = "authors_to_book")
@NamedQueries({
        @NamedQuery(name = BookAuthorEntity.getByBookAndAuthorIds, query = "SELECT bookKeyword FROM BookAuthorEntity bookKeyword WHERE bookKeyword.book.id=:bookId AND bookKeyword.author.id=:authorId")
})
public class BookAuthorEntity {

    public static final String getByBookAndAuthorIds = "getByBookAndAuthorIds";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName="ID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName="ID")
    private AuthorEntity author;

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

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookAuthorEntity that = (BookAuthorEntity) o;

        if (id != that.id) return false;
        //if (author != null ? !author.equals(that.author) : that.author != null) return false;
        //if (book != null ? !book.equals(that.book) : that.book != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
