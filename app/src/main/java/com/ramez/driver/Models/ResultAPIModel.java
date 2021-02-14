package com.ramez.driver.Models;


public class ResultAPIModel<T> {

//    public boolean success;
//    public List<String> message = new ArrayList<>();


    public T data;
    public ErrorModel error;
    public String message;
    public int status;


}
