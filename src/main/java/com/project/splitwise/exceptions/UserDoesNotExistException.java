package com.project.splitwise.exceptions;

public class UserDoesNotExistException extends Exception{
    @Override
    public String getMessage() {
        return "User with the given user id does not exist!";
    }
}
