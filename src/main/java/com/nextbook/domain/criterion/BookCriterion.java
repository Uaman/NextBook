package com.nextbook.domain.criterion;

import com.nextbook.domain.enums.*;
import com.nextbook.domain.pojo.Author;
import com.nextbook.domain.pojo.Category;
import com.nextbook.domain.pojo.Publisher;
import com.nextbook.domain.pojo.SubCategory;

/**
 * Created by Polomani on 27.07.2015.
 */
public class BookCriterion {

    private int from;

    private int max;

    private Category category;

    private SubCategory subCategory;

    private EighteenPlus eighteenPlus;

    private int yearOfPublication;

    private Publisher publisher;

    private BookTypeEnum bookType;

    private int numberOfPages;

    private Author author;

    private OrderDirectionEnum orderDirection;

    private BookOrderEnum orderBy;

    private Status status;

    private QueryType queryType;

    private BookCriterion(){}

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public EighteenPlus getEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(EighteenPlus eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public BookTypeEnum getBookType() {
        return bookType;
    }

    public void setBookType(BookTypeEnum bookType) {
        this.bookType = bookType;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public OrderDirectionEnum getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirectionEnum orderDirection) {
        this.orderDirection = orderDirection;
    }

    public BookOrderEnum getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(BookOrderEnum orderBy) {
        this.orderBy = orderBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public static class Builder{

        private BookCriterion bookCriterion;

        public Builder(){
            this.bookCriterion = new BookCriterion();
        }

        public BookCriterion build(){
            return this.bookCriterion;
        }

        public Builder from(int from){
            bookCriterion.setFrom(from);
            return this;
        }

        public Builder max(int max){
            bookCriterion.setMax(max);
            return this;
        }

        public Builder category(Category category){
            this.bookCriterion.setCategory(category);
            return this;
        }

        public Builder subcategory(SubCategory subcategory){
            this.bookCriterion.setSubCategory(subcategory);
            return this;
        }

        public Builder eighteenPlus(EighteenPlus eighteenPlus){
            this.bookCriterion.setEighteenPlus(eighteenPlus);
            return this;
        }

        public Builder yearOfPublication(int yearOfPublication){
            this.bookCriterion.setYearOfPublication(yearOfPublication);
            return this;
        }

        public Builder publisher(Publisher publisher){
            this.bookCriterion.setPublisher(publisher);
            return this;
        }

        public Builder bootType(BookTypeEnum bookType){
            this.bookCriterion.setBookType(bookType);
            return this;
        }

        public Builder numberOfPages(int numberOfPages){
            this.bookCriterion.setNumberOfPages(numberOfPages);
            return this;
        }

        public Builder author(Author author){
            this.bookCriterion.setAuthor(author);
            return this;
        }

        public Builder orderDirection(OrderDirectionEnum orderDirection){
            this.bookCriterion.setOrderDirection(orderDirection);
            return this;
        }

        public Builder orderBy(BookOrderEnum orderBy){
            this.bookCriterion.setOrderBy(orderBy);
            return this;
        }

        public Builder status(Status status){
            this.bookCriterion.setStatus(status);
            return this;
        }

        public Builder queryType(QueryType queryType){
            this.bookCriterion.setQueryType(queryType);
            return this;
        }
    }
}
