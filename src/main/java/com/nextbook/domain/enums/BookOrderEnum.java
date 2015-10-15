package com.nextbook.domain.enums;

/**
 * Created by Polomani on 06.08.2015.
 */
public enum BookOrderEnum {
    YEAR_OF_PUBLICATION,
    TYPE_OF_BOOK,
    NUMBER_OF_PAGES;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "book.yearOfPublication";
                break;
            case 1:
                name = "book.typeOfBook";
                break;
            case 3:
                name = "book.numberOfPages";
                break;
        }
        return name;
    }
}
