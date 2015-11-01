package com.nextbook.daoMongo.mongoEntities;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Dima on 30.09.2015.
 */
public class WatchedBookEnt {

    @Id
    private String documentId;

    private int userID;

    private int bookId;

    private Date actionTime;

    private TypeOfAction typeOfAction;

    public WatchedBookEnt(int userID, int bookId, Date actionTime, TypeOfAction typeOfAction) {
        this.userID = userID;
        this.bookId = bookId;
        this.actionTime = actionTime;
        this.typeOfAction = typeOfAction;
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

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(TypeOfAction typeOfAction) {
        this.typeOfAction = typeOfAction;
    }
}
