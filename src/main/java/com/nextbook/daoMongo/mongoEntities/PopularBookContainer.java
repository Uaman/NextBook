package com.nextbook.daoMongo.mongoEntities;

/**
 * Created by Dima on 19.10.2015.
 */
public class PopularBookContainer implements Comparable {

    private int bookId;

    private int popularity;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "PopularBookContainer{" +
                "bookId=" + bookId +
                ", popularity=" + popularity +
                '}';
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public int compareTo(Object o) {
        PopularBookContainer popularBook = (PopularBookContainer) o;
        return this.getPopularity() - popularBook.getPopularity();
    }
}
