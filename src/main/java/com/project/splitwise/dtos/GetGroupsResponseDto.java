package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GetGroupsResponseDto extends BaseResponseDto{
    List<UserGroup> userGroups;

    @Getter
    @Setter
    public static class UserGroup{
        private Long groupId;
        private String name;
        private String description;

    }

    @Override
    public void printResponse() {
        super.printResponse();
        if(getStatus() != null && getStatus().equals(ResponseStatusType.SUCCESS)){
            System.out.println("User is member of the below groups: ");
            for(UserGroup group: userGroups){
                System.out.println("ID: "+group.groupId+", Name: "+group.name);
            }
        }
    }
}
