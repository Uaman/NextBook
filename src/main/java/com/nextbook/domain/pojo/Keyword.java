package com.nextbook.domain.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:30 PM
 */
public class Keyword{

    private int id;

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
        return "Keyword{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
