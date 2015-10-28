package com.nextbook.daoMongo.mongoEntities;

import org.bson.BSONObject;
import org.bson.BsonDocument;
import org.bson.Document;
//import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Dima on 30.09.2015.
 */
public class WatchedBookEnt extends Document {


    private String documentId;

    private int userID;

    private int bookId;

    private long actionTime;

    private String typeOfAction;

    public WatchedBookEnt(int userID, int bookId, long actionTime, String typeOfAction) {
        this.userID = userID;
        this.bookId = bookId;
        this.actionTime = actionTime;
        this.typeOfAction = typeOfAction;
    }

    public Document getDocument(){
        Document entity = new Document();
        entity.append("userID",this.getUserID());
        entity.append("bookId",this.getBookId());
        entity.append("actionTime",this.getActionTime());
        entity.append("action",this.getTypeOfAction());
        return entity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(String typeOfAction) {
        this.typeOfAction = typeOfAction;
    }
}
