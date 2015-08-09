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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword1 = (Keyword) o;

        if (id != keyword1.id) return false;
        if (keyword != null ? !keyword.equals(keyword1.keyword) : keyword1.keyword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
