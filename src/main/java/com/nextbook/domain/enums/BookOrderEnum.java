package com.nextbook.domain.enums;

/**
 * Created by Polomani on 06.08.2015.
 */
public enum BookOrderEnum {
    ID,
    ISBN,
    EIGHTEEN_PLUS,
    YEAR_OF_PUBLICATION,
    LANGUAGE,
    TYPE_OF_BOOK,
    NUMBER_OF_PAGES;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "book.id";
                break;
            case 1:
                name = "book.isbn";
                break;
            case 2:
                name = "book.eighteenPlus";
                break;
            case 3:
                name = "book.yearOfPublication";
                break;
            case 4:
                name = "book.language";
                break;
            case 5:
                name = "book.typeOfBook";
                break;
            case 6:
                name = "book.numberOfPages";
                break;
        }
        return name;
    }
}
