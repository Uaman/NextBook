package com.nextbook.domain.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:50 PM
 */

@Entity
@Table(name = "keywords")
@NamedQueries({
        @NamedQuery(name = KeywordEntity.getByKeyword, query = "SELECT keyword FROM KeywordEntity keyword WHERE keyword.keyword=:keyword"),
        @NamedQuery(name = KeywordEntity.getKeywordsByPart, query = "SELECT keyword FROM KeywordEntity keyword WHERE keyword.keyword LIKE :keyword")
})
public class KeywordEntity {

    public static final String getByKeyword = "getKeywordByName";
    public static final String getKeywordsByPart = "getKeywordByKeyword";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "KEYWORD", nullable = false)
    private String keyword;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookKeywordEntity> bookKeywords = new ArrayList<BookKeywordEntity>();


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

    public List<BookKeywordEntity> getBookKeywords() {
        return bookKeywords;
    }

    public void setBookKeywords(List<BookKeywordEntity> bookKeywords) {
        this.bookKeywords = bookKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordEntity entity = (KeywordEntity) o;

        if (id != entity.id) return false;
        if (bookKeywords != null ? !bookKeywords.equals(entity.bookKeywords) : entity.bookKeywords != null)
            return false;
        if (keyword != null ? !keyword.equals(entity.keyword) : entity.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (bookKeywords != null ? bookKeywords.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "KeywordEntity{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
