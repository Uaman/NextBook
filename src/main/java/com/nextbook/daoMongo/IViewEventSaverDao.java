package com.nextbook.daoMongo;

/**
 * Created by Dima on 29.09.2015.
 */

import com.nextbook.daoMongo.mongoEntities.PopularBookContainer;
import com.nextbook.daoMongo.mongoEntities.WatchedBookEnt;

import java.util.Date;
import java.util.List;

/**
 *Interface for MongoDB Dao for saving events connected
 * with books which were looked through by user
 * Statistic!
 * */
public interface IViewEventSaverDao {

    public List<PopularBookContainer> getNBooks(Date from, Date to, int n);

    public void saveWatchedBookEnt(WatchedBookEnt entity);

    //public void saveWatchedBookEnt(int usedId, int bookId, Date eventTime, String typeOfAction);

}
