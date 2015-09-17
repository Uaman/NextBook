package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 14.09.2015.
 */
@Entity
@Table(name = "orders_with_book")
public class OrderedBookEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDERS_ID")
    private OrderEntity orderEnt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private BookEntity bookEnt;

    @Column(name = "NUMBER_OF_BOOKS")
    private int numberOfBooks;

    @Override
    public String toString() {
        return "OrderedBookEntity{" +
                "id=" + id +
                ", orderEnt=" + orderEnt +
                ", bookEnt=" + bookEnt +
                ", numberOfBooks=" + numberOfBooks +
                ", orderEntities=" + orderEntities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedBookEntity that = (OrderedBookEntity) o;

        if (getId() != that.getId()) return false;
        if (getNumberOfBooks() != that.getNumberOfBooks()) return false;
        if (getOrderEnt() != null ? !getOrderEnt().equals(that.getOrderEnt()) : that.getOrderEnt() != null)
            return false;
        if (getBookEnt() != null ? !getBookEnt().equals(that.getBookEnt()) : that.getBookEnt() != null) return false;
        return !(getOrderEntities() != null ? !getOrderEntities().equals(that.getOrderEntities()) : that.getOrderEntities() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getOrderEnt() != null ? getOrderEnt().hashCode() : 0);
        result = 31 * result + (getBookEnt() != null ? getBookEnt().hashCode() : 0);
        result = 31 * result + getNumberOfBooks();
        result = 31 * result + (getOrderEntities() != null ? getOrderEntities().hashCode() : 0);
        return result;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderEntity getOrderEnt() {
        return orderEnt;
    }

    public void setOrderEnt(OrderEntity orderEnt) {
        this.orderEnt = orderEnt;
    }

    public BookEntity getBookEnt() {
        return bookEnt;
    }

    public void setBookEnt(BookEntity bookEnt) {
        this.bookEnt = bookEnt;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }


    @ManyToOne(optional = false)
    private OrderEntity orderEntities;

    public OrderEntity getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(OrderEntity orderEntities) {
        this.orderEntities = orderEntities;
    }
}