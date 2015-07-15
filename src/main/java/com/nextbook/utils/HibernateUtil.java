package com.nextbook.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 7/14/2015
 * Time: 12:30 PM
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static void init(){
        try {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            init();
        return sessionFactory;
    }
}
