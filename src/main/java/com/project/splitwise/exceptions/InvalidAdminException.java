package com.project.splitwise.exceptions;

public class InvalidAdminException extends Exception{
    @Override
    public String getMessage() {
        return "Given user is not admin of the group!";
    }
}
