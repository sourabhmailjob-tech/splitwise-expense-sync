package com.project.splitwise.services;

public class Util {
    public static boolean isValidId(String id){
        try{
            Long.parseLong(id);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
