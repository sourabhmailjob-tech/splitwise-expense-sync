package com.project.splitwise.commands;

import com.project.splitwise.controllers.GroupController;
import com.project.splitwise.dtos.AddMemberRequestDto;
import com.project.splitwise.dtos.AddMemberResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AddMemberCommand implements Command{
    //u1 AddMember g1 u2
    private GroupController groupController;
    @Autowired
    public AddMemberCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inpWords = Arrays.stream(input.split(" ")).toList();
        if(inpWords.size()==4 && inpWords.get(1).equalsIgnoreCase(CommandKeywords.ADD_MEMBER)){
            return true;
        }
        return false;
    }

    @Override
    public void execute(String input) {
        List<String> inpWords = Arrays.stream(input.split(" ")).toList();
        String userId = inpWords.get(0);
        String groupId = inpWords.get(2);
        String memberId = inpWords.get(3);

        AddMemberRequestDto request = new AddMemberRequestDto();
        request.setUserId(userId);
        request.setGroupId(groupId);
        request.setMemberId(memberId);

        AddMemberResponseDto response =groupController.addMember(request);
        response.printResponse();
    }
}
