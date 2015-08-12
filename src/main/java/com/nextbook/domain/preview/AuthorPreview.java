package com.nextbook.domain.preview;

/**
 * Created with IntelliJ IDEA.
 * User: borsch
 * Date: 8/12/2015
 * Time: 9:23 AM
 */
public class AuthorPreview {

    private int id;

    private String name;

    public AuthorPreview(){

    }

    public AuthorPreview(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
