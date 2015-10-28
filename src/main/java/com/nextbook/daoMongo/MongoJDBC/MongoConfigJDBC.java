package com.nextbook.daoMongo.MongoJDBC;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Created by Dima on 19.10.2015.
 */
public class MongoConfigJDBC {
    private static MongoClient mongoClient;

    public static MongoClient getInstance(){
        if(mongoClient == null){
            mongoClient = getConnect("mongodb://NextBookMongoDB:z0DO.D1Iq9hvqP4P6J1lghP.ylbLdqzk.ki85xuwG2Q-@ds040898.mongolab.com:40898/NextBookMongoDB");
        }
        return mongoClient;
    }

    private static MongoClient getConnect(String uri){

        return new MongoClient(new MongoClientURI(uri));
        //return new MongoClient("localhost", 27017);
    }

}
