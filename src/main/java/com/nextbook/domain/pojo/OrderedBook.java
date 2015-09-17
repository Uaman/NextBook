package com.nextbook.domain.pojo;

/**
 * Created by Dima on 09.09.2015.
 */
public class OrderedBook {

    private Book orderedBook;

    private Order order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @Override
    public String toString() {
        return "OrderedBook{" +
                "orderedBook=" + orderedBook +
                ", theNumberOfBooks=" + theNumberOfBooks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedBook that = (OrderedBook) o;

        if (getTheNumberOfBooks() != that.getTheNumberOfBooks()) return false;
        return !(getOrderedBook() != null ? !getOrderedBook().equals(that.getOrderedBook()) : that.getOrderedBook() != null);

    }

    @Override
    public int hashCode() {
        int result = getOrderedBook() != null ? getOrderedBook().hashCode() : 0;
        result = 31 * result + getOrder().hashCode();
        result = 31 * result + getTheNumberOfBooks();
        return result;
    }

    public Book getOrderedBook() {

        return orderedBook;
    }

    public void setOrderedBook(Book orderedBook) {
        this.orderedBook = orderedBook;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTheNumberOfBooks() {
        return theNumberOfBooks;
    }

    public void setTheNumberOfBooks(int theNumberOfBooks) {
        this.theNumberOfBooks = theNumberOfBooks;
    }

    private int theNumberOfBooks;

}
