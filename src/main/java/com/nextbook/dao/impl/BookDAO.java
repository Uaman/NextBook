package com.nextbook.dao.impl;

import com.nextbook.dao.IBookDao;
import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.filters.BookCriterion;
import com.nextbook.domain.pojo.Book;
import com.nextbook.utils.DozerMapperFactory;
import com.nextbook.utils.HibernateUtil;
import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
            Query query = session.createQuery("SELECT book FROM BookEntity book");
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
            if(entities.size() > 0) {
                result = new ArrayList<Book>();
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

    private Query createQueryFromCriterion(Session session, BookCriterion criterion) {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT book FROM BookEntity book");

        boolean where = false;

        if(criterion.getId() > 0){
            queryString.append(" WHERE book.id=:id");
            where = true;
        }
        if(validString(criterion.getIsbn())){
            if(where) {
                queryString.append(" AND book.isbn=:isbn");
            } else {
                queryString.append(" WHERE book.isbn=:isbn");
            }
            where = true;
        }
        if (validString(criterion.getName())){
            if(where) {
                queryString.append(" AND (book.uaName LIKE :name");
            } else {
                queryString.append(" WHERE (book.uaName LIKE :name");
            }
            queryString.append(" OR book.ruName LIKE :name");
            queryString.append(" OR book.enName LIKE :name)");
            where = true;
        }
        if (validString(criterion.getState()) && !criterion.getState().equals("all")) {
            if(where) {
                queryString.append(" AND book.eighteenPlus=:eighteenPlus");
            } else {
                queryString.append(" WHERE book.eighteenPlus=:eighteenPlus");
            }
            where = true;
        }
        if(criterion.getYearOfPublication() > 0){
            if(where) {
                queryString.append(" AND book.yearOfPublication=:yearOfPublication");
            } else {
                queryString.append(" WHERE book.yearOfPublication=:yearOfPublication");
            }
            where = true;
        }
        if (validString(criterion.getLanguage())){
            if(where) {
                queryString.append(" AND book.language LIKE :language");
            } else {
                queryString.append(" WHERE book.language LIKE :language");
            }
            where = true;
        }
        if(criterion.getTypeOfBook() != null){
            if(where) {
                queryString.append(" AND book.typeOfBook=:typeOfBook");
            } else {
                queryString.append(" WHERE book.typeOfBook=:typeOfBook");
            }
            where = true;
        }
        if(criterion.getNumberOfPages() > 0){
            if(where) {
                queryString.append(" AND book.numberOfPages=:numberOfPages");
            } else {
                queryString.append(" WHERE book.numberOfPages=:numberOfPages");
            }
            where = true;
        }
        if (validString(criterion.getSubCategory())){
            if(where) {
                queryString.append(" AND (book.subCategoryEntity.nameUa LIKE :subCategory");
            } else {
                queryString.append(" WHERE (book.subCategoryEntity.nameUa LIKE :subCategory");
            }
            queryString.append(" OR book.subCategoryEntity.nameRu LIKE :subCategory");
            queryString.append(" OR book.subCategoryEntity.nameEn LIKE :subCategory)");
            where = true;
        }
        if (validString(criterion.getPublisher())){
            if(where) {
                queryString.append(" AND (book.publisherEntity.nameUa LIKE :publisher");
            } else {
                queryString.append(" WHERE (book.publisherEntity.nameUa LIKE :publisher");
            }
            queryString.append(" OR book.publisherEntity.nameRu LIKE :publisher");
            queryString.append(" OR book.publisherEntity.nameEn LIKE :publisher)");
            where = true;
        }
        if(criterion.getAuthorId() > 0){
            if(where) {
                queryString.append(" AND book.authorEntity.id=:authorId");
            } else {
                queryString.append(" WHERE book.authorEntity.id=:authorId");
            }
            where = true;
        }

        Query result = session.createQuery(queryString.toString());
        result.setProperties(criterion);

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
