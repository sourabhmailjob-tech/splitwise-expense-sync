package com.project.splitwise.exceptions;

public class UnAuthorizedException extends Exception{
    @Override
    public String getMessage() {
        return "Un-Authorized Access!";
    }
}
