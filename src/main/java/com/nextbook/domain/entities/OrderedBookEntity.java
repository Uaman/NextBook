package com.nextbook.domain.entities;

import javax.persistence.*;

/**
 * Created by Dima on 14.09.2015.
 */
@Entity
@Table(name = "ORDERS_WITH_BOOK")
public class OrderedBookEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private OrderEntity order;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity orderedBook;

    @Column(name = "number_of_books")
    private int numberOfBooks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public BookEntity getOrderedBook() {
        return orderedBook;
    }

    public void setOrderedBook(BookEntity orderedBook) {
        this.orderedBook = orderedBook;
    }

    public int getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(int numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedBookEntity that = (OrderedBookEntity) o;

        if (id != that.id) return false;
        if (numberOfBooks != that.numberOfBooks) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (orderedBook != null ? !orderedBook.equals(that.orderedBook) : that.orderedBook != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (orderedBook != null ? orderedBook.hashCode() : 0);
        result = 31 * result + numberOfBooks;
        return result;
    }

    @Override
    public String toString() {
        return "OrderedBookEntity{" +
                "id=" + id +
                ", order=" + order +
                ", orderedBook=" + orderedBook +
                ", numberOfBooks=" + numberOfBooks +
                '}';
    }
}