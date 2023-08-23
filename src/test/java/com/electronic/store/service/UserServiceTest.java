package com.electronic.store.service;

import com.electronic.store.dto.RoleDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.model.Role;
import com.electronic.store.model.User;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.RoleRepository;
import com.electronic.store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mockito;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.util.*;


@SpringBootTest
public class UserServiceTest {


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    Role role;
    User  user;



    String roleId="abc";

    @BeforeEach
    public void init() {
        role=Role.builder().roleId("abcd").roleName("NORMAL").build();
        user = User.builder()
                .name("Aarti")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .roles(Set.of(role))
                .password("asdD25")
                .build();;



    }


    @Test
    public void createUserTest() {
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        RoleDto roleDto = this.modelMapper.map(role, RoleDto.class);
        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.createUser(userDto1);
        Assertions.assertEquals("Aarti",userDto1.getName());

    }

    @Test
    public void updateUserTest() {
        String userId = "";
        RoleDto roleDto = this.modelMapper.map(role, RoleDto.class);
        UserDto userDto = UserDto.builder()
                .name("Poonam")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .roles(Set.of(roleDto))
                .password("asdD25")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updateUserDto = userService.updateUser(userDto, userId);
        System.out.println(updateUserDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(updateUserDto.getName(), user.getName(), "Test case fail update !!!");

    }

    @Test
    public void deleteUserTest() throws IOException {
        String userId = "userId123";
        Mockito.when(userRepository.findById("userId123")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void getAllUserTest() {
        User user1 = User.builder()
                .name("swati")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();
        User user2 = User.builder()
                .name("neha")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();


        List<User> userlist = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userlist);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asd");

        Assertions.assertEquals(3, allUser.getContent().size(), "Test Fail getAlluser !!!");

    }

    @Test
    public void getUserByIdTest() {
        String userId = "asd";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(userId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "Test Fail for getuser by userId !!!");

    }

    @Test
    public void getUserByEmailTest() {
        String email = "aarti@gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(email);
        Assertions.assertNotNull(userDto);
        System.out.println(userDto.getEmail());
        Assertions.assertEquals(user.getName(), userDto.getName(), "Test Fail email not match !!!");

    }

    @Test
    public void searchUser() {
        String keywords = "swati";
        User user1 = User.builder()
                .name("swati")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();
        User user2 = User.builder()
                .name("swati avdha")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();


        List<User> userlist = Arrays.asList(user1, user2);
        Mockito.when(userRepository.findByNameContaining(Mockito.anyString())).thenReturn(userlist);

        List<UserDto> userDtos = userService.searchUser(keywords);
        Assertions.assertEquals(2, userDtos.size(), "Test Fail for search user by keyword !!!");

    }

}
