package com.nextbook.daoMongo.MongoJDBC;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.nextbook.daoMongo.IViewEventSaverDao;
import com.nextbook.daoMongo.mongoEntities.PopularBookContainer;
import com.nextbook.daoMongo.mongoEntities.WatchedBookEnt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Dima on 19.10.2015.
 */
@Aspect
public class ViewEventSaverDaoJDBC implements IViewEventSaverDao {

    private final String DBNAME = "NextBookMongoDB";

    private final String COLLECTIONNAME = "test";

    @Override
    public List<PopularBookContainer> getNBooks(Date from, Date to, int n) {
        DBCollection colect = MongoConfigJDBC.getInstance().getDB(DBNAME).getCollection("test");
        String mapFunction = "function(){" +"var dateFrom = new ISODate("+from.getTime()+"); " +
                "var dateTo = new ISODate("+to.getTime()+"); "+
//                "var date = new Date(this.actionTime.getTime());"+
                "if((this.actionTime >= "+from.getTime()+") && (this.actionTime <= "+to.getTime()+"))"+
                "emit(this[\"bookId\"],{count: 1});}";


        String reduceFunction = "function(key, values) {\n" +
                "\tvar sum = 0;\n" +
                "\tvalues.forEach(function(value) {\n" +
                "\t\tsum += value['count'];\n" +
                "\t});\n" +
                "\treturn {count: sum};\n" +
                "};";

        //mongoOperations.ex
        MapReduceCommand mapRed = new MapReduceCommand(colect,mapFunction,reduceFunction,
                null, MapReduceCommand.OutputType.INLINE,null);
        MapReduceOutput out = colect.mapReduce(mapRed);

        return getSortedList(out,n);
    }

    private List<PopularBookContainer> getSortedList(MapReduceOutput mapReduceOutput, int n){
        List<PopularBookContainer> books = new ArrayList<PopularBookContainer>();
        int i = 0;
        for(DBObject o : mapReduceOutput.results()){
            if(i == n) break;
            System.out.println(o);
            PopularBookContainer popularBook = new PopularBookContainer();
            Double doub = new Double((Double) o.get("_id"));
            popularBook.setBookId(doub.intValue());
            BasicDBObject bs = (BasicDBObject) o.get("value");
            doub = new Double((Double) bs.get("count"));
            popularBook.setPopularity(doub.intValue());
            books.add(popularBook);
            i++;
        }
        Collections.sort(books);
        return books;
    }

    @Override
    public void saveWatchedBookEnt(WatchedBookEnt entity) {
        MongoDatabase db = MongoConfigJDBC.getInstance().getDatabase(DBNAME);
        MongoCollection collection = db.getCollection(COLLECTIONNAME);
        collection.insertOne(entity.getDocument());
    }

    //@After("execution(* com.nextbook.controllers.book.BookViewController.infoBook(..))")
    public void saveWatchedBookEnt(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            System.out.print("Arg"+(i+1)+":"+args[i]);
        }
        //saveWatchedBookEnt(new WatchedBookEnt(usedId,bookId,eventTime.getTime(),typeOfAction));
    }
}
