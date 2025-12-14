package com.project.splitwise.services;

import com.project.splitwise.exceptions.*;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private UserRepository userRepository;
   private GroupRepository groupRepository;

   @Autowired
    public GroupService(GroupRepository groupRepository,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(String  userId, String groupName) throws UserDoesNotExistException {
        if(!Util.isValidId(userId)){
            throw new UserDoesNotExistException();
        }

        Long id = Long.parseLong(userId);
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            Group group = new Group();
            group.setCreatedBy(user);
            group.setName(groupName);
            group.setMembers(List.of(user));
            return groupRepository.save(group);
        }else{
            throw new UserDoesNotExistException();
        }

    }

    public Group addMember(String userId, String groupId,String newUserId ) throws UserDoesNotExistException, GroupDoesNotExistException, MemberAlreadyExistsException, InvalidIdException, UnAuthorizedException {
       if(!Util.isValidId(userId) || !Util.isValidId(newUserId)){
           throw new InvalidIdException();
       }
       if(!Util.isValidId(groupId)){
           throw new InvalidIdException();
       }
       Long adminId = Long.parseLong(userId);
       Long existingGroupId = Long.parseLong(groupId);
       Long memberId = Long.parseLong(newUserId);

       Optional<User> userOptional = userRepository.findById(adminId);
       if(userOptional.isEmpty()){
           throw new UserDoesNotExistException();
       }
        Optional<Group> groupOptional = groupRepository.findById(existingGroupId);
        if(groupOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }

        Optional<User> memberOptional = userRepository.findById(memberId);
        if(memberOptional.isEmpty()){
            throw new UserDoesNotExistException();
        }

       Group group = groupOptional.get();
       User user = userOptional.get();
       User member = memberOptional.get();

       if(group.getCreatedBy().getId() != user.getId()){
           throw new UnAuthorizedException();
       }

        for(User memberUser: group.getMembers()){
            if(memberUser.getId() == member.getId()){
                throw new MemberAlreadyExistsException();
            }
        }

       group.getMembers().add(member);
       return groupRepository.save(group);

    }






}
