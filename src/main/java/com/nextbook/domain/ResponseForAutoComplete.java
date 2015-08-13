package com.nextbook.domain;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/13/2015
 * Time: 10:46 AM
 */
public class ResponseForAutoComplete {

    private int id;
    private String value;

    public ResponseForAutoComplete(){

    }

    public ResponseForAutoComplete(int id, String value){
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
