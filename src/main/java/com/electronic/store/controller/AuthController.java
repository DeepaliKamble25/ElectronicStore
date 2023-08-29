package com.electronic.store.controller;

import com.electronic.store.dto.JwtRequest;
import com.electronic.store.dto.JwtResponse;
import com.electronic.store.dto.UserDto;
import com.electronic.store.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping
public class AuthController {

  private  static Logger logger= LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserDetailsService userDetailservice;


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){


        logger.info("Initialize request login : {}"+request);

        this.doauthenticate(request.getEmail(),request.getPassword());

        logger.info("Initialize doauthenticate login : {}",request.getEmail(),request.getPassword());

        UserDetails userDetails = userDetailservice.loadUserByUsername(request.getEmail());

        logger.info("Initialize request userDetailservice in login : {}"+userDetails);
        String token = this.helper.generateToken(userDetails);

        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
        logger.info("Completed request login : {}"+userDto);
        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto)
                .build();

        return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.ACCEPTED);



    }
    private void doauthenticate(String email, String password) {
        logger.info("Initialize doauthenticate login : {}", email, password);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
            logger.info("Completed doauthenticate login : {}", email, password);
        } catch (BadCredentialsException b) {
            throw new BadCredentialsException(" Invalid UserName or password Exception !!!");

        }
    }



    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal){


        String name = principal.getName();

//        UserDto userDto = this.modelMapper.map(userDetailservice, UserDto.class);

        return new ResponseEntity<>(this.modelMapper.map(userDetailservice.loadUserByUsername(name),UserDto.class), HttpStatus.OK);

    }




}
