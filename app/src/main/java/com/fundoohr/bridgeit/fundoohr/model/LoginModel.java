package com.fundoohr.bridgeit.fundoohr.model;

/**
 * Created by bridgeit on 6/1/17.
 * Purpose:
 * It Will Contains The Data Object Only WhereIn If You Declare The Object
 * Private You Need To Use Getter And Setter.It Will Have The State And
 * Behaviour Of The Class.
 */


public class LoginModel {
    //decalre the variables
    private int statusCode;
    private String message;
    private String token;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {

        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
