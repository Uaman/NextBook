package com.nextbook.daoMongo;

//import com.mongodb.MongoClientURI;
//import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoURI;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.Cloud;
//import org.springframework.cloud.CloudFactory;
//import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

//import com.mongodb.MongoClient;

import javax.activation.DataSource;
import java.util.Properties;


/**
 * Created by Dima on 03.10.2015.
 */
@Configuration
//@PropertySource(value = "env.properties"/*, ignoreResourceNotFound=true*/)
public class MongoConfig {

    private static String collectionName = "watchedBookStatistic";

    //@Value("${database.mongo.urlLocal}")
    private String localUrl;

    //@Value("${database.mongo.name}")
    private String databaseName;

    public static String getCollectionName() {
        return collectionName;
    }



    public @Bean
    MongoDbFactory mongoDbFactory() throws Exception {
//        CloudFactory cloudFactory = new CloudFactory();
//        Cloud cloud = cloudFactory.getCloud();
//        MongoServiceInfo serviceInfo = (MongoServiceInfo) cloud.getServiceInfo("my-mongodb");
//        String serviceID = serviceInfo.getId();
//        return cloud.getServiceConnector(serviceID,SimpleMongoDbFactory.class, null);

        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "NBMongoDBLab");

    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

        return mongoTemplate;

    }

    public @Bean
    MongoDbFactory mongoDbFactoryProd() throws Exception {
                return new SimpleMongoDbFactory(
                        new MongoURI("mongodb://NewUser:56fdgY23mjh156@ds040888.mongolab.com:40888/NBMongoDBLab"));

    }

    public @Bean
    MongoTemplate mongoTemplateProd() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactoryProd());

        return mongoTemplate;

    }

}
