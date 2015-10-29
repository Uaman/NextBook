package com.nextbook.dao.impl;

import com.nextbook.dao.Dao;
import com.nextbook.dao.IBookDao;
import com.nextbook.domain.entities.*;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.EighteenPlus;
import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.enums.QueryType;
import com.nextbook.domain.enums.Status;
import com.nextbook.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:47 PM
 */
@Service
@Transactional
public class BookDAO implements IBookDao {

    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private Dao baseDao;

    @Override
    public BookEntity getBookById(int bookId) {
        return baseDao.getById(BookEntity.class, bookId);
    }

    @Override
    public List<BookEntity> getAllBooks() {
        List<BookEntity> result =
                baseDao.executeNamedQueryWithParams(
                        BookEntity.class,
                        BookEntity.getAllBooks,
                        new HashMap<String, Object>() {{
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Override
    public int getBooksQuantity() {
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getBooksQuantity);
            result = ((Long) query.iterate().next()).intValue();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public int getCountByCriterion(BookCriterion criterion){
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            result = ((Long) query.iterate().next()).intValue();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public boolean deleteBook(int bookId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getById);
            query.setParameter("id", bookId);
            List<BookEntity> list = query.list();
            if(list != null && list.size() > 0) {
                session.delete(list.get(0));
            }
            session.getTransaction().commit();
            deleted = true;
        } catch (Exception e){
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return deleted;
    }

    @Override
    public BookEntity updateBook(BookEntity book) {
        return baseDao.attachWithMerge(book);
    }

    @Override
    public boolean isbnExist(final String isbn) {
        List<BookEntity> result =
                baseDao.executeNamedQueryWithParams(
                        BookEntity.class,
                        BookEntity.getByIsbn,
                        new HashMap<String, Object>() {{
                            put("isbn", isbn);
                        }});
        return !(result == null || result.isEmpty());
    }

    @Override
    public List<BookEntity> getBooksByCriterion(BookCriterion criterion) {
        List<BookEntity> entities = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            entities = query.list();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return entities;
    }

    @Override
    public List<BookEntity> getAllPublisherBooks(final int publisherId) {
        List<BookEntity> result =
                baseDao.executeNamedQueryWithParams(
                        BookEntity.class,
                        BookEntity.getBooksByPublisherId,
                        new HashMap<String, Object>() {{
                            put("id", publisherId);
                        }});
        return (result == null || result.isEmpty()) ? null : result;
    }

    @Override
    public BookKeywordEntity getBookToKeyword(final int bookId, final int keywordId){
        List<BookKeywordEntity> result =
                baseDao.executeNamedQueryWithParams(
                        BookKeywordEntity.class,
                        BookKeywordEntity.getByBookAndKeywordIds,
                        new HashMap<String, Object>(){{
                            put("bookId", bookId);
                            put("keywordId", keywordId);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }

    @Override
    public BookKeywordEntity updateBookToKeyword(BookKeywordEntity bookKeyword){
        return baseDao.attachWithMerge(bookKeyword);
    }

    @Override
    public boolean deleteBookToKeyword(final int bookId, final int keywordId) {
        return baseDao.deleteByNamedQueryWithParams(
                BookKeywordEntity.class,
                BookKeywordEntity.getByBookAndKeywordIds,
                new HashMap<String, Object>() {{
                    put("bookId", bookId);
                    put("keywordId", keywordId);
                }});
    }

    @Override
    public BookAuthorEntity getBookToAuthor(final int bookId, final int authorId) {
        List<BookAuthorEntity> result =
                baseDao.executeNamedQueryWithParams(
                        BookAuthorEntity.class,
                        BookAuthorEntity.getByBookAndAuthorIds,
                        new HashMap<String, Object>(){{
                            put("bookId", bookId);
                            put("authorId", authorId);
                        }});
        return (result == null || result.isEmpty()) ? null : result.get(0);
    }

    @Override
    public BookAuthorEntity updateBookToAuthor(BookAuthorEntity bookAuthor){
        return baseDao.attachWithMerge(bookAuthor);
    }

    @Override
    public boolean deleteBookToAuthor(final int bookId, final int authorId) {
        return baseDao.deleteByNamedQueryWithParams(
                        BookAuthorEntity.class,
                        BookAuthorEntity.getByBookAndAuthorIds,
                        new HashMap<String, Object>() {{
                            put("bookId", bookId);
                            put("authorId", authorId);
                        }});
    }

    @Override
    public UserStarsBookEntity userStarsBookUpdate(UserStarsBookEntity userStarsBook) {
        return baseDao.attachWithMerge(userStarsBook);
    }

    private Query createQueryFromCriterion(Session session, BookCriterion criterion) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT ");
        if(criterion.getQueryType() == QueryType.COUNT)
            queryString.append(" COUNT(book) FROM BookEntity book");
        else
            queryString.append(" DISTINCT book FROM BookEntity book");

        Map<String, Object> params = new HashMap<String, Object>();

        boolean where = false;

        if (criterion.getEighteenPlus() != null && criterion.getEighteenPlus() != EighteenPlus.ALL) {
            boolean eighteenPlus = false;
            if(criterion.getEighteenPlus() == EighteenPlus.ONLY_EIGHTEEN_PLUS)
                eighteenPlus = true;
            if(where) {
                queryString.append(" AND book.eighteenPlus=:eighteenPlus");
            } else {
                queryString.append(" WHERE book.eighteenPlus=:eighteenPlus");
            }
            where = true;
            params.put("eighteenPlus", eighteenPlus);
        }
        if(criterion.getYearOfPublication() > 0){
            if(where) {
                queryString.append(" AND book.yearOfPublication=:yearOfPublication");
            } else {
                queryString.append(" WHERE book.yearOfPublication=:yearOfPublication");
            }
            where = true;
            params.put("yearOfPublication", criterion.getYearOfPublication());
        }
        if(criterion.getBookType() != null && criterion.getBookType() != BookTypeEnum.ALL){
            if(where) {
                queryString.append(" AND book.typeOfBook=:typeOfBook");
            } else {
                queryString.append(" WHERE book.typeOfBook=:typeOfBook");
            }
            where = true;
            params.put("typeOfBook", criterion.getBookType());
        }
        if(criterion.getNumberOfPages() > 0){
            if(where) {
                queryString.append(" AND book.numberOfPages=:numberOfPages");
            } else {
                queryString.append(" WHERE book.numberOfPages=:numberOfPages");
            }
            where = true;
            params.put("numberOfPages", criterion.getNumberOfPages());
        }
        if (criterion.getSubCategory() != null){
            if(where) {
                queryString.append(" AND book.subCategoryEntity.id=:subCategory");
            } else {
                queryString.append(" WHERE book.subCategoryEntity.id=:subCategory");
            }
            where = true;
            params.put("subCategory", criterion.getSubCategory().getId());
        } else if (criterion.getCategory() != null){
            if(where) {
                queryString.append(" AND book.subCategoryEntity.categoryEntity.id=:category");
            } else {
                queryString.append(" WHERE book.subCategoryEntity.categoryEntity.id=:category");
            }
            where = true;
            params.put("category", criterion.getCategory().getId());
        }
        if (criterion.getPublisher() != null){
            if(where) {
                queryString.append(" AND book.publisherEntity.id=:publisher");
            } else {
                queryString.append(" WHERE book.publisherEntity.id=:publisher");
            }
            params.put("publisher", criterion.getPublisher().getId());
            where = true;
        }
        /*
        if(criterion.getAuthor() != null){
            if(where) {
                queryString.append(" AND book.bookToAuthor.author.id=:author");
            } else {
                queryString.append(" WHERE book.bookToAuthor.author.id=:author");
            }
            where = true;
            params.put("author", criterion.getAuthor().getId());
        }*/
        if(criterion.getStatus() != null && criterion.getStatus() != Status.ALL){
            if(where) {
                queryString.append(" AND book.status=:status");
            } else {
                queryString.append(" WHERE book.status=:status");
            }
            where = true;
            params.put("status", criterion.getStatus());
        }

        if(criterion.getOrderDirection()!=null && criterion.getOrderBy()!=null){
            queryString.append(" ORDER BY " + criterion.getOrderBy() + ' ' + criterion.getOrderDirection());
        }

        Query result = session.createQuery(queryString.toString());

        Set<String> keys = params.keySet();
        for(String key : keys)
            result.setParameter(key, params.get(key));

        if(criterion.getFrom() > 0)
            result.setFirstResult(criterion.getFrom());

        if(criterion.getMax() > 0)
            result.setMaxResults(criterion.getMax());

        return result;
    }

    private boolean validString(String s){
        return s != null && !s.equals("") && !s.equals("%null%") && !s.equals("%%");
    }
}
