package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 5:50 PM
 */

@Entity
@Table(name = "keywords")
@NamedQueries({
        @NamedQuery(name = KeywordEntity.getByKeyword, query = "SELECT keyword FROM KeywordEntity keyword WHERE keyword.keyword=:keyword")
})
public class KeywordEntity {

    public static final String getByKeyword = "getKeywordByName";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "KEYWORD", nullable = false)
    private String keyword;

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

    @Override
    public String toString() {
        return "KeywordEntity{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
