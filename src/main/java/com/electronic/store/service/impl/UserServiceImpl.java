package com.electronic.store.service.impl;

import com.electronic.store.dto.UserDto;
import com.electronic.store.exception.UserNotFoundException;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        //generate userId in String format
        logger.info("Initiating request to saveUser");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = this.modelMapper.map(userDto, User.class);
//        user.setUserId(userId);
        User saveUser = userRepository.save(user);
        logger.info("Completing request to saved");
        return this.modelMapper.map(saveUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating request to updateUser by findById method"+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setImage(userDto.getImage());
        user.setGender(userDto.getImage());
        user.setEmail(userDto.getEmail());
        User updatedUser = this.userRepository.save(user);
        logger.info("Completing request to update user by findByid"+userId);
        return  this.modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating request to deleteUser"+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));
        this.userRepository.delete(user);
        logger.info("Completing request to Deleted UserById "+userId);
    }

    @Override
    public List<UserDto> getAllUser() {
        logger.info("Initiating request to get AllUser");
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completing request to get AllUser");

        return userDtos;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating request to get AllUser find UserById "+userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + userId));
        logger.info("Completing request to get UserById"+userId);

        return this.modelMapper.map(user, UserDto.class);
    }
    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating request to find UserByEmail"+email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ApiConstant.User_Not_Found + email));
        logger.info("Completing request to find UserByEmail"+email);
        return this.modelMapper.map(user,UserDto.class);
    }
    @Override
    public List<UserDto> searchUser(String keywords) {
        logger.info("Initiating request to searchUser"+keywords);
        List<User> users = this.userRepository.findByNameContaining(keywords);
        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completing request to searchUser"+keywords);
        return userDtos;
    }


}
