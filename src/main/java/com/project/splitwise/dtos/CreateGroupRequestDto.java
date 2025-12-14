package com.project.splitwise.dtos;

import com.project.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGroupRequestDto {
    private String creatorId;
    private String groupName;
}
