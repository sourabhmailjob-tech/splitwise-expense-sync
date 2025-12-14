package com.project.splitwise.exceptions;

public class InvalidIdException extends Exception{
    @Override
    public String getMessage() {
        return "Given Id is invalid!";
    }
}
