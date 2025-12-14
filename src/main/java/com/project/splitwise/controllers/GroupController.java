package com.project.splitwise.controllers;

import com.project.splitwise.dtos.*;
import com.project.splitwise.exceptions.*;
import com.project.splitwise.models.Group;
import com.project.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupController {
    private GroupService groupService;


    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public CreateGroupResponseDto createGroup(CreateGroupRequestDto request){
        CreateGroupResponseDto response = new CreateGroupResponseDto();

        try{
            groupService.createGroup(request.getCreatorId(), request.getGroupName());
            response.setStatus(ResponseStatusType.SUCCESS);
            response.setMessage("Group created successfully..");

        }catch(UserDoesNotExistException userDoesNotExistException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(userDoesNotExistException.getMessage());
        }
        return response;
    }


    public AddMemberResponseDto addMember(AddMemberRequestDto request){
        AddMemberResponseDto response = new AddMemberResponseDto();
        Group group;

        try{
            group = groupService.addMember(request.getUserId(),
                                            request.getGroupId(),
                                            request.getMemberId());
            response.setGroupId(group.getId());
            response.setStatus(ResponseStatusType.SUCCESS);
            response.setMessage("New member added successfully..");
        }catch(UserDoesNotExistException userDoesNotExistException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(userDoesNotExistException.getMessage());
        }catch (GroupDoesNotExistException groupDoesNotExistException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(groupDoesNotExistException.getMessage());
        }catch (MemberAlreadyExistsException memberAlreadyExistsException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(memberAlreadyExistsException.getMessage());
        }catch (InvalidIdException invalidIdException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(invalidIdException.getMessage());
        }catch (UnAuthorizedException unAuthorizedException){
            response.setStatus(ResponseStatusType.FAILURE);
            response.setMessage(unAuthorizedException.getMessage());
        }

        return response;
    }






}
