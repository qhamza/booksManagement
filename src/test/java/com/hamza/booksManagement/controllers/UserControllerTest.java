package com.hamza.booksManagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamza.booksManagement.entities.User;
import com.hamza.booksManagement.exceptions.UserServiceException;
import com.hamza.booksManagement.mapstruct.dtos.UserDto;
import com.hamza.booksManagement.mapstruct.dtos.UserGetDto;
import com.hamza.booksManagement.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    private String asJsonString(final Object obj) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldGetUsers() throws Exception {

        UserGetDto user1 = new UserGetDto("first1", "last1", "email1");
        user1.setId(1);
        UserGetDto user2 = new UserGetDto("first2", "last2", "email2");
        user2.setId(2);
        UserGetDto user3 = new UserGetDto("first3", "last3", "email3");
        user3.setId(3);

        List<UserGetDto> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        when(this.userService.findAllUsers()).thenReturn(users);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetUsers() throws Exception {

        when(this.userService.findAllUsers()).thenThrow(new Exception());

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/all"))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldGetGivenUser() throws Exception {

        UserGetDto user = new UserGetDto();
        user.setId(1);
        user.setFirstname("hamza");
        user.setLastname("qanit");
        user.setEmail("email@example.com");

        when(this.userService.findUserById(anyInt())).thenReturn(user);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/find/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetUserByNonexistentId() throws Exception {
        when(this.userService.findUserById(anyInt())).thenThrow(new UserServiceException("user not found"));

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/find/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotGetUserByInvalidId() throws Exception {
        when(this.userService.findUserById(anyInt())).thenThrow(new Exception());

        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/find/0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotGetUserByBadArgument() throws Exception {
        this.mockMvc.perform( MockMvcRequestBuilders
                        .get("/users/find/\"1\""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDto user = new UserDto();
        user.setId(1);
        user.setFirstname("null");
        user.setLastname("rossi");
        user.setEmail("mail");

        when(this.userService.addUser(any())).thenReturn(this.modelMapper.map(user, User.class));

        this.mockMvc.perform(post("/users/add")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateUserGivenNullProperty() throws Exception {

        UserDto user = new UserDto();
        user.setId(1);
        user.setFirstname(null);
        user.setLastname("rossi");
        user.setEmail("mail");

        when(this.userService.addUser(any())).thenThrow(new NullPointerException());

        this.mockMvc.perform(post("/users/add")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void shouldUpdateGivenUser() throws Exception {
        UserDto user = new UserDto();
        user.setId(3);
        user.setFirstname("hamza");
        user.setLastname("qanit");

        when(this.userService.updateUserById(anyInt(), any())).thenReturn(this.modelMapper.map(user, User.class));

        this.mockMvc.perform(post("/users/update/3")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdateUserBecauseNotFound() throws Exception {
        UserDto user = new UserDto();
        user.setId(3);
        user.setFirstname("hamza");
        user.setLastname("qanit");

        when(this.userService.updateUserById(anyInt(), any())).thenThrow(new UserServiceException("user not found"));

        this.mockMvc.perform(post("/users/update/4")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdateUserBecauseOfNullProperty() throws Exception {
        UserDto user = new UserDto();
        user.setId(3);
        user.setFirstname(null);
        user.setLastname("qanit");

        when(this.userService.updateUserById(anyInt(), any())).thenThrow(new NullPointerException());

        this.mockMvc.perform(post("/users/update/4")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
    @Test
    void shouldNotUpdateUserBecauseOfInvalidId() throws Exception {
        UserDto user = new UserDto();
        user.setId(3);
        user.setFirstname("null");
        user.setLastname("qanit");

        when(this.userService.updateUserById(anyInt(), any())).thenThrow(new Exception());

        this.mockMvc.perform(post("/users/update/0")
                        .content(asJsonString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldDeleteUser() throws Exception {

        User user = new User();
        user.setId(5);
        user.setFirstname("unit");
        user.setLastname("deleteTest");

        when(this.userService.deleteUser(anyInt())).thenReturn(user);

        this.mockMvc.perform(delete("/users/delete/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(user))));
    }

    @Test
    void shouldNotDeleteUserByInvalidId() throws Exception {

        when(this.userService.deleteUser(anyInt())).thenThrow(new Exception("invalid Id"));

        this.mockMvc.perform(delete("/users/delete/0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotDeleteNonexistentUser() throws Exception {

        when(this.userService.deleteUser(anyInt())).thenThrow(new UserServiceException("user not found"));

        this.mockMvc.perform(delete("/users/delete/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}