package com.electronic.store.controller;

import com.electronic.store.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping
public class AuthController {


    @Autowired
    private UserDetailsService userDetailservice;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){


        String name = principal.getName();
        UserDto userDto = this.modelMapper.map(userDetailservice, UserDto.class);

        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }




}
