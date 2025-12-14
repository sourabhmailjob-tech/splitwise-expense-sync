package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMemberRequestDto {
    private String userId;
    private String groupId;
    private String memberId;

}
