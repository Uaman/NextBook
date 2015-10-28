//package com.nextbook.daoMongo.impl;
//
////import com.mongodb.Mongo;
////import com.mongodb.MongoClient;
////import com.mongodb.ServerAddress;
//import com.nextbook.daoMongo.IViewEventSaverDao;
//import com.nextbook.daoMongo.MongoConfig;
//import com.nextbook.daoMongo.mongoEntities.TypeOfAction;
//import com.nextbook.daoMongo.mongoEntities.WatchedBookEnt;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Repository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Date;
//
//
///**
// * Created by Dima on 30.09.2015.
// */
//@Repository
//public class ViewEventSaverDao implements IViewEventSaverDao {
//
//    @Override
//    public void saveWatchedBookEnt(WatchedBookEnt entity) {
//        ApplicationContext ctx =
//                new AnnotationConfigApplicationContext(MongoConfig.class);
//        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
//        mongoOperation.insert(entity,"test");
//    }
//
//    @Override
//    public void saveWatchedBookEnt(int usedId, int bookId, Date eventTime, TypeOfAction typeOfAction) {
//        WatchedBookEnt bookEnt = new WatchedBookEnt(usedId,bookId,eventTime,typeOfAction);
//        saveWatchedBookEnt(bookEnt);
//    }
//
//
//}
