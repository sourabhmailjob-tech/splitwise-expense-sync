package com.project.splitwise.controllers;

import com.project.splitwise.dtos.*;
import com.project.splitwise.dtos.RegisterUserRequestDto;
import com.project.splitwise.dtos.RegisterUserResponseDto;
import com.project.splitwise.dtos.UpdateProfileRequestDto;
import com.project.splitwise.dtos.UpdateProfileResponseDto;
import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.exceptions.UserDoesNotExistException;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto  registerUser(RegisterUserRequestDto request){
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();

        try{
            user = userService.registerUser(request.getUserName(),
                                            request.getPhoneNumber(),
                                            request.getPassword());

            response.setUserId(user.getId());
            response.setStatus(ResponseStatusType.SUCCESS);
            response.setMessage("Registered user successfully..");

        }catch (UserAlreadyExistsException userAlreadyExistsException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(userAlreadyExistsException.getMessage());

        }

        return response;
    }

    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto request){
        UpdateProfileResponseDto response = new UpdateProfileResponseDto();
        try{
             userService.updateProfile(request.getUserId(), request.getPassword());
             response.setStatus(ResponseStatusType.SUCCESS);
             response.setMessage("Profile updated successfully..");
        }catch(UserDoesNotExistException userDoesNotExistException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(userDoesNotExistException.getMessage());
            return response;
        }

        return response;
    }

    public GetGroupsResponseDto getGroupsOfUser(GetGroupsRequestDto request){
        GetGroupsResponseDto response = new GetGroupsResponseDto();
        try{
            List<Group> groups = userService.getGroupsOfUser(request.getUserId());
            List<GetGroupsResponseDto.UserGroup> userGroups = new ArrayList<>();
            for(Group group: groups){
                GetGroupsResponseDto.UserGroup userGroup = new GetGroupsResponseDto.UserGroup();
                userGroup.setGroupId(group.getId());
                userGroup.setName(group.getName());
                userGroup.setDescription(group.getDiscription());
                userGroups.add(userGroup);
            }
            response.setUserGroups(userGroups);
            response.setStatus(ResponseStatusType.SUCCESS);
            response.setMessage("Groups feched successfully..");

        }catch(Exception e){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
