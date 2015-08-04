package com.nextbook.domain.filters;

/**
 * Created by Stacy on 7/29/15.
 */

public class AuthorCriterion {

    private int from;

    private int max;

    private String firstName;

    private String lastName;

    public AuthorCriterion(){

    }

    public AuthorCriterion(String keyword){
        this(keyword, keyword);
    }

    public AuthorCriterion(String fName, String lName){
        this.firstName = fName;
        this.lastName = lName;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
