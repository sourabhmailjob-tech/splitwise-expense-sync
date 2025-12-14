package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseDto {
    private ResponseStatusType status;
    private String message;

    public void printResponse(){
        if(status != null){
            System.out.println(status);
        }
        if(message != null){
            System.out.println(": ");
            System.out.print(message);
        }
        System.out.println();
    }


}
