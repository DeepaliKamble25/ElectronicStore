package com.electronic.store.controller;

import com.electronic.store.dto.UserDto;
import com.electronic.store.model.User;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private User user;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Poonam")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();
    }

    @Test
    public void createUserTest() throws Exception {

        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        this.mockMvc.perform
                        (MockMvcRequestBuilders.post("/users/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJson(user))
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    @Test
    public void updateUserTest() throws Exception {
//users/update/{userId}+ PUT request +json
        String userId = "De12";
        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(user))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());


    }

    @Test
    public void deleteUserTest() throws Exception {
        String userId="userId123";

        Mockito.verify(userService,Mockito.times(1)).deleteUser(userId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userId)).andReturn();
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/" +userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    //get by Id
    @Test
    public void getAllUserTest() throws Exception {
        UserDto userDto1 = UserDto.builder().name("Poonam").email("aarti@gmail.com").about("this is teating user method for servive").gender("female").image("asd.jpeg").password("asdD25").build();
        UserDto userDto2 = UserDto.builder().name("Praniti").email("praniti@gmail.com").about("this is teating user method for servive").gender("female").image("asd.jpeg").password("asdD26").build();
        UserDto userDto3 = UserDto.builder().name("Prashika").email("prashika@gmail.com").about("this is teating user method for servive").gender("male").image("asd.jpeg").password("asdD27").build();

        //UserDto userDto1=new UserDto("asdf","dip","")

        PageableResponse<UserDto> pageableReasponse = new PageableResponse<>();
        pageableReasponse.setContent(Arrays.asList(userDto1,userDto2,userDto3));
        pageableReasponse.setLastPage(false);
        pageableReasponse.setPageNumber(100);
        pageableReasponse.setTotalElements(1000);
        pageableReasponse.setPageSize(10);
        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableReasponse);
   this.mockMvc.perform(MockMvcRequestBuilders.get("/users/getAll")
                   .contentType(MediaType.APPLICATION_JSON)
                   .accept(MediaType.APPLICATION_JSON)
           )
           .andDo(print())
           .andExpect(status().isOk());

    }

    private String convertObjectToJson(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}