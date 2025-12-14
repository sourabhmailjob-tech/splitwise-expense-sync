package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupResponseDto extends BaseResponseDto{
    private Long groupId;

    @Override
    public void printResponse() {
        super.printResponse();
        if(getStatus() != null && getMessage().equals(ResponseStatusType.SUCCESS)){
            System.out.println("Group_Id: "+groupId);
        }
    }
}
