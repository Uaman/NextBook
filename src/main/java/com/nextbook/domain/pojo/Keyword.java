package com.nextbook.domain.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:30 PM
 */
public class Keyword{

    private int id;

    private String keyword;

    private List<BookKeyword> bookToKeywords = new ArrayList<BookKeyword>();

    public int getId() {
            return id;
        }

    public void setId(int id) {
            this.id = id;
        }

    public String getKeyword() {
            return keyword;
        }

    public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

    public List<BookKeyword> getBookToKeywords() {
        return bookToKeywords;
    }

    public void setBookToKeywords(List<BookKeyword> bookToKeywords) {
        this.bookToKeywords = bookToKeywords;
    }

    public void addBookToKeyword(BookKeyword bookKeyword){
        if (bookToKeywords == null)
            bookToKeywords = new ArrayList<BookKeyword>();
        bookToKeywords.add(bookKeyword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword1 = (Keyword) o;

        if (id != keyword1.id) return false;
        if (bookToKeywords != null ? !bookToKeywords.equals(keyword1.bookToKeywords) : keyword1.bookToKeywords != null)
            return false;
        if (keyword != null ? !keyword.equals(keyword1.keyword) : keyword1.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (bookToKeywords != null ? bookToKeywords.hashCode() : 0);
        return result;
    }
}
