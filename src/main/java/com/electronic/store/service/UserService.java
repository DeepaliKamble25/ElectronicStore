package com.electronic.store.service;

import com.electronic.store.dto.UserDto;

import java.util.List;


public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId) ;


    //delete
    void deleteUser(String userId) ;

    //get all
    List<UserDto> getAllUser();
//    get single user

    UserDto getUserByEmail(String email);

    //get by id
    UserDto getUserById(String userId);

//search
    List<UserDto> searchUser(String keywords);

}
