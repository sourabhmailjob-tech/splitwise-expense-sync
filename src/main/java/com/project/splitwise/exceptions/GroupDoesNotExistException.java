package com.project.splitwise.exceptions;

public class GroupDoesNotExistException extends Exception{
    @Override
    public String getMessage() {
        return "Group with the given group id does not exist!";
    }
}
