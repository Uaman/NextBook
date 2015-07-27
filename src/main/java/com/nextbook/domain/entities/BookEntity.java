package com.nextbook.domain.entities;

import com.nextbook.domain.enums.BookTypeEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:26 PM
 */

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(name = BookEntity.getById, query = "SELECT book FROM BookEntity book WHERE book.id=:id"),
        @NamedQuery(name = BookEntity.getByIsbn, query = "SELECT book FROM BookEntity book WHERE book.isbn=:isbn")
})
public class BookEntity {

    public static final String getById = "getBookById";
    public static final String getByIsbn = "getBookByIsbn";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "ISBN", nullable = false)
    private String isbn;

    @Column(name = "NAME_UA", nullable = false)
    private String uaName;

    @Column(name = "NAME_EN")
    private String enName;

    @Column(name = "NAME_RU")
    private String ruName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SUBCATEGORY_ID", nullable = false)
    private SubCategoryEntity subCategoryEntity;

    @Column(name = "EIGHTEEN_PLUS")
    private boolean eighteenPlus;

    @Column(name = "YEAR_OF_PUBLICATION", nullable = false)
    private int yearOfPublication;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    private PublisherEntity publisherEntity;

    @Column(name = "LANGUAGE", nullable = false)
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_OF_BOOK", nullable = false)
    private BookTypeEnum typeOfBook;

    @Column(name = "PAGES_NUMBER")
    private int numberOfPages;

    @Column(name = "DESCRIPTION_UA")
    private String descriptionUa;

    @Column(name = "DESCRIPTION_EN")
    private String descriptionEn;

    @Column(name = "DESCRIPTION_RU")
    private String descriptionRu;

    @Column(name = "LINK")
    private String link;

    @Column(name = "LINK_TO_STORAGE")
    private String linkToStorage;

    @Column(name = "NUMBER_OF_IMG_FOR_COVER")
    private int numberOfImagesForCover;

    @Column(name = "NUMBER_OF_IMG_IN_GALERY")
    private int numberOfImagesInGallery;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "keywords_to_book", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = { @JoinColumn(name = "KEYWORD_ID") })
    private List<KeywordEntity> keywords = new ArrayList<KeywordEntity>();

    @ManyToOne
    @JoinTable(name = "authors_to_book", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")})
    private AuthorEntity authorEntity;

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

    public SubCategoryEntity getSubCategoryEntity() {
        return subCategoryEntity;
    }

    public void setSubCategoryEntity(SubCategoryEntity subCategoryEntity) {
        this.subCategoryEntity = subCategoryEntity;
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

    public PublisherEntity getPublisherEntity() {
        return publisherEntity;
    }

    public void setPublisherEntity(PublisherEntity publisherEntity) {
        this.publisherEntity = publisherEntity;
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

    public List<KeywordEntity> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeywordEntity> keywords) {
        this.keywords = keywords;
    }

    public AuthorEntity getAuthorEntity() {
        return authorEntity;
    }

    public void setAuthorEntity(AuthorEntity authorEntity) {
        this.authorEntity = authorEntity;
    }
}

