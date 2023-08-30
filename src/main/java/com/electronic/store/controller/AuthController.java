package com.electronic.store.controller;

import com.electronic.store.dto.JwtRequest;
import com.electronic.store.dto.JwtResponse;
import com.electronic.store.dto.UserDto;
import com.electronic.store.model.User;
import com.electronic.store.security.JwtHelper;
import com.electronic.store.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebSignature;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserDetailsService userDetailservice;

    @Value("${googleClientId}")
    private String googleClientId;
    @Value("${newPassword}")
    private String newPassword;

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {


        logger.info("Initialize request login : {}" + request);

        this.doauthenticate(request.getEmail(), request.getPassword());

        logger.info("Initialize doauthenticate login : {}", request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailservice.loadUserByUsername(request.getEmail());

        logger.info("Initialize request userDetailservice in login : {}" + userDetails);
        String token = this.helper.generateToken(userDetails);

        UserDto userDto = this.modelMapper.map(userDetails, UserDto.class);
        logger.info("Completed request login : {}" + userDto);
        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .user(userDto)
                .build();

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.ACCEPTED);


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
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {


        String name = principal.getName();

//        UserDto userDto = this.modelMapper.map(userDetailservice, UserDto.class);

        return new ResponseEntity<>(this.modelMapper.map(userDetailservice.loadUserByUsername(name), UserDto.class), HttpStatus.OK);

    }
    //login with google.33


    @PostMapping("/google")
    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String, Object> data) throws IOException {

        //get the idToken from request
        String idToken = data.get("idToken").toString();

        NetHttpTransport netHttpTransport = new NetHttpTransport();

        JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier.Builder verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jacksonFactory).setAudience(Collections.singleton(googleClientId));

        GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), idToken);

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        logger.info("Payload : {}", payload);

        String email = payload.getEmail();

        User user = null;

       user = userService.findUserByEmailOptional(email).orElse(null);

       if(user==null){

          user= this.saveUser(email,data.get("name").toString(),data.get("photourl").toString());
       }
        ResponseEntity<JwtResponse> jwtResponseResponseEntity = this.login(JwtRequest.builder().email(user.getEmail()).password(newPassword).build());

       return jwtResponseResponseEntity;
    }


    private User saveUser(String email,String name,String photourl){

        UserDto newUser = UserDto.builder()
                .name(name)
                .email(email)
                .password(newPassword)
                .image(photourl)
                .roles(new HashSet<>())
                .build();

        UserDto user = userService.createUser(newUser);

        return this.modelMapper.map(user,User.class);
    }

}
