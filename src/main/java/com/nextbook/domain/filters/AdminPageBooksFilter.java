package com.nextbook.domain.filters;

import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.enums.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 10/15/2015
 * Time: 10:50 PM
 */
public class AdminPageBooksFilter {

    private int category;

    private int subCategory;

    private EighteenPlus eighteenPlus;

    private int yearOfPublication;

    private int publisher;

    private BookTypeEnum bookType;

    private int numberOfPages;

    private int author;

    private OrderDirectionEnum orderDirection;

    private BookOrderEnum orderBy;

    private Status status;

    public AdminPageBooksFilter(){}

    public AdminPageBooksFilter(BookCriterion criterion){
        if(criterion.getCategory() != null)
            this.category = criterion.getCategory().getId();
        if(criterion.getSubCategory() != null)
            this.subCategory = criterion.getSubCategory().getId();
        if(criterion.getPublisher() != null)
            this.publisher = criterion.getPublisher().getId();
        if(criterion.getAuthor() != null)
            this.author = criterion.getAuthor().getId();
        this.eighteenPlus = criterion.getEighteenPlus();
        this.bookType = criterion.getBookType();
        this.orderDirection = criterion.getOrderDirection();
        this.orderBy = criterion.getOrderBy();
        this.status = criterion.getStatus();
        this.yearOfPublication = criterion.getYearOfPublication();
        this.numberOfPages = criterion.getNumberOfPages();
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BookOrderEnum getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(BookOrderEnum orderBy) {
        this.orderBy = orderBy;
    }

    public OrderDirectionEnum getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirectionEnum orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public BookTypeEnum getBookType() {
        return bookType;
    }

    public void setBookType(BookTypeEnum bookType) {
        this.bookType = bookType;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public EighteenPlus getEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(EighteenPlus eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public int getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }
}
