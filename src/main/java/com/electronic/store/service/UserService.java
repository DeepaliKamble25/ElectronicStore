package com.electronic.store.service;

import com.electronic.store.dto.UserDto;
import com.electronic.store.playload.PageableResponse;

import java.util.List;


public interface UserService {

    //create
    UserDto createUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto,String userId) ;


    //delete
    void deleteUser(String userId) ;

    //get all
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
//    get single user

    UserDto getUserByEmail(String email);

    //get by id
    UserDto getUserById(String userId);

//search
    List<UserDto> searchUser(String keywords);

}
