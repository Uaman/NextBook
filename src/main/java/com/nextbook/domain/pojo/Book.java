package com.nextbook.domain.pojo;

import com.nextbook.domain.enums.BookTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 7:45 PM
 */
public class Book {

    private int id;

    private String isbn;

    private String uaName;

    private String enName;

    private String ruName;

    private SubCategory subCategory;

    private boolean eighteenPlus;

    private int yearOfPublication;

    private Publisher publisher;

    private String language;

    private BookTypeEnum typeOfBook;

    private int numberOfPages;

    private String descriptionUa;

    private String descriptionEn;

    private String descriptionRu;

    private String link;

    private String linkToStorage;

    private int numberOfImagesForCover;

    private int numberOfImagesInGallery;

    private List<Keyword> keywords;

    private List<Author> authors;

    private List<BookAuthor> bookToAuthor;

    private List<BookKeyword> bookToKeywords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkToStorage() {
        return linkToStorage;
    }

    public void setLinkToStorage(String linkToStorage) {
        this.linkToStorage = linkToStorage;
    }

    public int getNumberOfImagesForCover() {
        return numberOfImagesForCover;
    }

    public void setNumberOfImagesForCover(int numberOfImagesForCover) {
        this.numberOfImagesForCover = numberOfImagesForCover;
    }

    public int getNumberOfImagesInGallery() {
        return numberOfImagesInGallery;
    }

    public void setNumberOfImagesInGallery(int numberOfImagesInGallery) {
        this.numberOfImagesInGallery = numberOfImagesInGallery;
    }

    public List<Keyword> getKeywords() {
        if(keywords == null)
            keywords = new ArrayList<Keyword>();
        if(keywords.size() == 0) {
            for (BookKeyword bookKeyword : bookToKeywords) {
                keywords.add(bookKeyword.getKeyword());
            }
        }
        return keywords;
    }

    public void addKeyword(Keyword keyword){
        if (keywords == null)
            getKeywords();
        keywords.add(keyword);
    }

    public List<BookAuthor> getBookToAuthor() {
        return bookToAuthor;
    }

    public void setBookToAuthor(List<BookAuthor> bookToAuthor) {
        this.bookToAuthor = bookToAuthor;
    }

    public List<Author> getAuthors() {
        if(authors == null)
            authors = new ArrayList<Author>();
        if(authors.size() == 0) {
            for (BookAuthor bookAuthor : bookToAuthor) {
                authors.add(bookAuthor.getAuthor());
            }
        }
        return authors;
    }

    public void addAuthor(Author author){
        if(authors == null)
            getAuthors();
        authors.add(author);
    }

    public List<BookKeyword> getBookToKeywords() {
        return bookToKeywords;
    }

    public void setBookToKeywords(List<BookKeyword> bookToKeywords) {
        this.bookToKeywords = bookToKeywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (eighteenPlus != book.eighteenPlus) return false;
        if (id != book.id) return false;
        if (numberOfImagesForCover != book.numberOfImagesForCover) return false;
        if (numberOfImagesInGallery != book.numberOfImagesInGallery) return false;
        if (numberOfPages != book.numberOfPages) return false;
        if (yearOfPublication != book.yearOfPublication) return false;
        if (authors != null ? !authors.equals(book.authors) : book.authors != null) return false;
        if (bookToAuthor != null ? !bookToAuthor.equals(book.bookToAuthor) : book.bookToAuthor != null) return false;
        if (bookToKeywords != null ? !bookToKeywords.equals(book.bookToKeywords) : book.bookToKeywords != null)
            return false;
        if (descriptionEn != null ? !descriptionEn.equals(book.descriptionEn) : book.descriptionEn != null)
            return false;
        if (descriptionRu != null ? !descriptionRu.equals(book.descriptionRu) : book.descriptionRu != null)
            return false;
        if (descriptionUa != null ? !descriptionUa.equals(book.descriptionUa) : book.descriptionUa != null)
            return false;
        if (enName != null ? !enName.equals(book.enName) : book.enName != null) return false;
        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (keywords != null ? !keywords.equals(book.keywords) : book.keywords != null) return false;
        if (language != null ? !language.equals(book.language) : book.language != null) return false;
        if (link != null ? !link.equals(book.link) : book.link != null) return false;
        if (linkToStorage != null ? !linkToStorage.equals(book.linkToStorage) : book.linkToStorage != null)
            return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;
        if (ruName != null ? !ruName.equals(book.ruName) : book.ruName != null) return false;
        if (subCategory != null ? !subCategory.equals(book.subCategory) : book.subCategory != null) return false;
        if (typeOfBook != book.typeOfBook) return false;
        if (uaName != null ? !uaName.equals(book.uaName) : book.uaName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (uaName != null ? uaName.hashCode() : 0);
        result = 31 * result + (enName != null ? enName.hashCode() : 0);
        result = 31 * result + (ruName != null ? ruName.hashCode() : 0);
        result = 31 * result + (subCategory != null ? subCategory.hashCode() : 0);
        result = 31 * result + (eighteenPlus ? 1 : 0);
        result = 31 * result + yearOfPublication;
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (typeOfBook != null ? typeOfBook.hashCode() : 0);
        result = 31 * result + numberOfPages;
        result = 31 * result + (descriptionUa != null ? descriptionUa.hashCode() : 0);
        result = 31 * result + (descriptionEn != null ? descriptionEn.hashCode() : 0);
        result = 31 * result + (descriptionRu != null ? descriptionRu.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (linkToStorage != null ? linkToStorage.hashCode() : 0);
        result = 31 * result + numberOfImagesForCover;
        result = 31 * result + numberOfImagesInGallery;
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (bookToAuthor != null ? bookToAuthor.hashCode() : 0);
        result = 31 * result + (bookToKeywords != null ? bookToKeywords.hashCode() : 0);
        return result;
    }
}
