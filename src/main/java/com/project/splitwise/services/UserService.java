package com.project.splitwise.services;

import com.project.splitwise.exceptions.UserAlreadyExistsException;
import com.project.splitwise.exceptions.UserDoesNotExistException;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.models.UserStatus;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String userName, String phoneNumber, String password) throws UserAlreadyExistsException {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        //Chek if user exists as active/invited or not and do further
        if(userOptional.isPresent()){
            if(userOptional.get().getUserStatus().equals(UserStatus.ACTIVE)){
                throw new UserAlreadyExistsException();
            }else{
                User user = userOptional.get();
                user.setUserStatus(UserStatus.ACTIVE);
                user.setName(userName);
                user.setPassword(password);
                return userRepository.save(user);
            }
        }

        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setName(userName);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    public User updateProfile(String userId, String password) throws UserDoesNotExistException {
        if(!Util.isValidId(userId)){
            throw new UserDoesNotExistException();
        }

        Long id = Long.parseLong(userId);
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setPassword(password);
            return userRepository.save(user);
        }else{
            throw new UserDoesNotExistException();
        }
    }

    public List<Group> getGroupsOfUser(String userId) throws UserDoesNotExistException {
        if(!Util.isValidId(userId)){
            throw new UserDoesNotExistException();
        }

        Long id = Long.parseLong(userId);
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user.getGroups();
        }else{
            throw new UserDoesNotExistException();
        }
    }

}
