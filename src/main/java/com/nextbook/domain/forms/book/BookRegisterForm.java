package com.nextbook.domain.forms.book;

import com.nextbook.domain.enums.BookTypeEnum;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 9:55 PM
 */
public class BookRegisterForm {

    private int bookId;

    @NotNull(message = "book.registration.isbn.require")
    @Pattern(message = "book.registration.isbn.format", regexp = "(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
    private String isbn;

    @NotNull(message = "book.registration.name.ua.require")
    private String uaName;

    private String enName;

    private String ruName;

    private boolean eighteenPlus;

    @Min(value = MIN_YEAR_OF_PUBLICATION, message = "book.registration.year.value")
    @Max(value = MAX_YEAR_OF_PUBLICATION, message = "book.registration.year.value")
    private int yearOfPublication;

    private String language;

    @NotNull(message = "book.registration.type.choose.one")
    private BookTypeEnum typeOfBook;

    private int numberOfPages;

    @NotNull(message = "book.registration.description.ua.require")
    @Size(min = MIN_DESCRIPTION_LENGTH, message = "book.registration.description.ua.length")
    private String descriptionUa;

    private String descriptionEn;

    private String descriptionRu;

    private int subCategoryId;

    @Size(min = MIN_NUMBER_OF_KEYWORDS, message = "book.registration.keywords.min.count")
    private List<String> keywords = new ArrayList<String>();

    @Size(min = MIN_NUMBER_OF_AUTHORS, message = "book.registration.author.require")
    private List<Integer> authors;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUaName() {
        return uaName;
    }

    public void setUaName(String uaName) {
        this.uaName = uaName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BookTypeEnum getTypeOfBook() {
        return typeOfBook;
    }

    public void setTypeOfBook(BookTypeEnum typeOfBook) {
        this.typeOfBook = typeOfBook;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public List<Integer> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Integer> authors) {
        this.authors = authors;
    }


    public static final int MIN_YEAR_OF_PUBLICATION = 1000;
    public static final int MAX_YEAR_OF_PUBLICATION = 3000;

    public static final int MIN_DESCRIPTION_LENGTH = 50;

    public static final int MIN_NUMBER_OF_KEYWORDS = 5;
    public static final int MIN_NUMBER_OF_AUTHORS = 1;
}
