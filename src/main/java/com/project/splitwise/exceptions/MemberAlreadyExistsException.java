package com.project.splitwise.exceptions;

public class MemberAlreadyExistsException extends Exception{
    @Override
    public String getMessage() {
        return "User is already a member of the group!";
    }
}
