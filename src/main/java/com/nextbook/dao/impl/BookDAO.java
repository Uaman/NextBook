package com.nextbook.dao.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.entities.BookAuthorEntity;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.entities.BookKeywordEntity;
import com.nextbook.domain.entities.UserStarsBookEntity;
import com.nextbook.domain.enums.BookTypeEnum;
import com.nextbook.domain.enums.EighteenPlus;
import com.nextbook.domain.criterion.BookCriterion;
import com.nextbook.domain.enums.QueryType;
import com.nextbook.domain.enums.Status;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.pojo.BookAuthor;
import com.nextbook.domain.pojo.BookKeyword;
import com.nextbook.domain.pojo.UserStarsBook;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/23/2015
 * Time: 4:47 PM
 */
@Repository
public class BookDAO implements IBookDao {

    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public Book getBookById(int bookId) {
        Book result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getById);
            query.setParameter("id", bookId);
            List<BookEntity> list = query.list();
            if(list != null && list.size() > 0) {
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), Book.class);
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getAllBooks);
            List<BookEntity> entities = query.list();
            if(entities.size() > 0) {
                result = new ArrayList<Book>();
                for (BookEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Book temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Book.class);
                            if (temp != null)
                                result.add(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
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
    public Book updateBook(Book book) {
        Book result = null;
        if(book != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                BookEntity entity = DozerMapperFactory.getDozerBeanMapper().map(book, BookEntity.class);
                entity = (BookEntity) session.merge(entity);
                result = DozerMapperFactory.getDozerBeanMapper().map(entity, Book.class);
                session.getTransaction().commit();
            } catch (Exception e) {
                if(session != null && session.getTransaction().isActive())
                    session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                if (session != null && session.isOpen())
                    session.close();
            }
        }
        return result;
    }

    @Override
    public boolean isbnExist(String isbn) {
        boolean exist = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getByIsbn);
            query.setParameter("isbn", isbn);
            List<BookEntity> list = query.list();
            if(list != null && list.size() > 0) {
                exist = true;
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return exist;
    }

    @Override
    public List<Book> getBooksByCriterion(BookCriterion criterion) {
        List<Book> result = null;
        Session session = sessionFactory.openSession();
        try {
            List<BookEntity> entities = null;
            session.beginTransaction();
            Query query = createQueryFromCriterion(session, criterion);
            entities = query.list();
            result = new ArrayList<Book>();
            if(entities.size() > 0) {
                for (BookEntity entity : entities) {
                    if (entity != null) {
                        try {
                            Book temp = dozerBeanMapper.map(entity, Book.class);
                            if (temp != null)
                                result.add(temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public List<Book> getAllPublisherBooks(int publisherId) {
        List<Book> result = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.getNamedQuery(BookEntity.getBooksByPublisherId);
            query.setParameter("id", publisherId);
            List<BookEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = new ArrayList<Book>();
                for(BookEntity entity : list){
                    Book temp = DozerMapperFactory.getDozerBeanMapper().map(entity, Book.class);
                    if(temp != null)
                        result.add(temp);
                }
            }
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }

        return result;
    }

    @Override
    public BookKeyword getBookToKeyword(int bookId, int keywordId){
        BookKeyword result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Query query = session.getNamedQuery(BookKeywordEntity.getByBookAndKeywordIds);
            query.setParameter("bookId", bookId);
            query.setParameter("keywordId", keywordId);
            List<BookKeywordEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), BookKeyword.class);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public BookKeyword updateBookToKeyword(BookKeyword bookKeyword){
        BookKeyword result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            BookKeywordEntity entity = DozerMapperFactory.getDozerBeanMapper().map(bookKeyword, BookKeywordEntity.class);
            entity = (BookKeywordEntity) session.merge(entity);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, BookKeyword.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public boolean deleteBookToKeyword(int bookId, int keywordId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(BookKeywordEntity.getByBookAndKeywordIds);
            query.setParameter("bookId", bookId);
            query.setParameter("keywordId", keywordId);
            List<BookKeywordEntity> list = query.list();
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
    public BookAuthor getBookToAuthor(int bookId, int authorId) {
        BookAuthor result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Query query = session.getNamedQuery(BookAuthorEntity.getByBookAndAuthorIds);
            query.setParameter("bookId", bookId);
            query.setParameter("authorId", authorId);
            List<BookAuthorEntity> list = query.list();
            if(list != null && list.size() > 0){
                result = DozerMapperFactory.getDozerBeanMapper().map(list.get(0), BookAuthor.class);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public BookAuthor updateBookToAuthor(BookAuthor bookAuthor){
        BookAuthor result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            BookAuthorEntity entity = DozerMapperFactory.getDozerBeanMapper().map(bookAuthor, BookAuthorEntity.class);
            entity = (BookAuthorEntity) session.merge(entity);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, BookAuthor.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public boolean deleteBookToAuthor(int bookId, int authorId) {
        boolean deleted = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            Query query = session.getNamedQuery(BookAuthorEntity.getByBookAndAuthorIds);
            query.setParameter("bookId", bookId);
            query.setParameter("authorId", authorId);
            List<BookAuthorEntity> list = query.list();
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
    public UserStarsBook userStarsBook(UserStarsBook userStarsBook) {
        UserStarsBook result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            UserStarsBookEntity entity = DozerMapperFactory.getDozerBeanMapper().map(userStarsBook, UserStarsBookEntity.class);
            entity = (UserStarsBookEntity)session.merge(entity);
            result = DozerMapperFactory.getDozerBeanMapper().map(entity, UserStarsBook.class);
            session.getTransaction().commit();
        } catch (Exception e){
            if(session != null && session.getTransaction().isActive())
                session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if(session != null && session.isOpen())
                session.close();
        }
        return result;
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
