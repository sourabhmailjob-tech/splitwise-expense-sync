package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponseDto extends BaseResponseDto {
    private Long userId;

    @Override
    public void printResponse() {
        super.printResponse();
        if(getStatus() != null && getStatus().equals(ResponseStatusType.SUCCESS)){
            System.out.println("User Id: "+userId);
        }
    }
}
