package com.nextbook.daoMongo;

/**
 * Created by Dima on 29.09.2015.
 */


import com.nextbook.daoMongo.mongoEntities.TypeOfAction;
import com.nextbook.daoMongo.mongoEntities.WatchedBookEnt;

import java.util.Date;

/**
 *Interface for MongoDB Dao for saving events connected
 * with books which were looked through by user
 * Statistic!
 * */
public interface IViewEventSaverDao {

    public void saveWatchedBookEnt(WatchedBookEnt entity);

    public void saveWatchedBookEnt(int usedId, int bookId, Date eventTime, TypeOfAction typeOfAction);

}
