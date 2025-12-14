package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetGroupsRequestDto extends BaseResponseDto {
  private String userId;
}
