package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDto{
    private String userName;
    private String phoneNumber;
    private String password;
}
