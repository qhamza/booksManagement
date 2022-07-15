package com.hamza.booksManagement.services;

import com.hamza.booksManagement.entities.User;
import com.hamza.booksManagement.exceptions.UserServiceException;
import com.hamza.booksManagement.mapstruct.dtos.UserGetDto;
import com.hamza.booksManagement.mapstruct.dtos.UserPostDto;
import com.hamza.booksManagement.repositories.UserRepository;
import org.modelmapper.MappingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean(name="userRepository")
    UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void shouldGetUsers() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstname("first1");
        user1.setLastname("last1");
        User user2 = new User();
        user2.setId(2);
        user2.setFirstname("first2");
        user2.setLastname("last2");
        User user3 = new User();
        user3.setId(3);
        user3.setFirstname("first3");
        user3.setLastname("last3");

        List<User> usersList = new ArrayList<>();
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);

        when(this.userRepository.findAll()).thenReturn(usersList);

        List<UserGetDto> gotUsers = this.userService.findAllUsers();

        verify(this.userRepository).findAll();

        System.out.println(gotUsers.get(0).getFirstname());

        assertNotNull(gotUsers);
    }

    @Test
    public void shouldReturnEmptyUsers(){
        List<User> usersList = new ArrayList<>();
        when(this.userRepository.findAll()).thenReturn(usersList);

        Exception thrown = assertThrows(Exception.class, () -> {
            List<UserGetDto> gotUsers = this.userService.findAllUsers();
        });

        assertEquals("users is empty", thrown.getMessage());
        verify(this.userRepository).findAll();
    }

    @Test
    public void shouldReturnUserByID() throws Exception {
        User user = new User();
        user.setId(2);
        user.setFirstname("hamza");
        user.setLastname("hamza");

        when(this.userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        UserGetDto gotUser = this.userService.findUserById(2);
        assertEquals(user.getId(), gotUser.getId());
        assertNotNull(gotUser);
        System.out.println(gotUser.getFirstname());
    }


    @Test
    public void shouldThrowInvalidIdExceptionWhenGettingUser() {
        User user = new User();
        user.setId(2);
        user.setFirstname("hamza");
        user.setLastname("hamza");

        when(this.userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        Exception e = assertThrows(Exception.class, ()->{
            UserGetDto gotUser = this.userService.findUserById(0);
        });

        assertEquals("id not valid", e.getMessage());

    }

    @Test
    public void shouldThrowIdNotFoundExceptionWhenGettingUser() {

        when(this.userRepository.findById(anyInt())).thenReturn(Optional.<User>empty());

        UserServiceException e = assertThrows(UserServiceException.class, ()->{
            UserGetDto gotUser = this.userService.findUserById(2);
        });

        assertEquals("user not found by given id", e.getMessage());

    }

    @Test
    public void shouldAddUser(){
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname("unit");
        newUser.setLastname("test");

        User result = this.userService.addUser(newUser);

        assertEquals(expectedUser, result);
    }

    @Test
    public void shouldNotAddUserByNullPointer(){
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("test");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname(null);
        newUser.setLastname("test");

        assertThrows(MappingException.class, ()->{
            User result = this.userService.addUser(newUser);
        });
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);
        when(this.userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname("unit");
        newUser.setLastname("test");

        User result = this.userService.updateUserById(1, newUser);

        assertEquals(expectedUser, result);
    }

    @Test
    public void shouldNotUpdateUserGivenBadId(){
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);
        when(this.userRepository.findById(anyInt())).thenReturn(Optional.<User>empty());

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname("unit");
        newUser.setLastname("test");

        UserServiceException e = assertThrows(UserServiceException.class, ()->{
            User result = this.userService.updateUserById(2, newUser);
        });

        assertEquals("user not found by given id", e.getMessage());
    }

    @Test
    public void shouldNotUpdateUserGivenInvalidId(){
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);
        when(this.userRepository.findById(anyInt())).thenReturn(Optional.<User>empty());

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname("unit");
        newUser.setLastname("test");

        Exception e = assertThrows(Exception.class, ()->{
            User result = this.userService.updateUserById(0, newUser);
        });

        assertEquals("id not valid", e.getMessage());
    }

    @Test
    public void shouldNotUpdateUserGivenInvalidInput(){
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.save(any())).thenReturn(expectedUser);
        when(this.userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        UserPostDto newUser = new UserPostDto();
        newUser.setFirstname(null);
        newUser.setLastname("test");

        MappingException e = assertThrows(MappingException.class, ()->{
            User result = this.userService.updateUserById(1, newUser);
        });
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.findById(1)).thenReturn(Optional.of(expectedUser));
        when(this.userRepository.deleteById(1)).thenReturn(expectedUser);

        User result = this.userService.deleteUser(1);

        assertEquals(expectedUser, result);
    }

    @Test
    public void shouldNotDeleteUserGivenInvalidId() throws Exception {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.findById(1)).thenReturn(Optional.of(expectedUser));
        when(this.userRepository.deleteById(1)).thenReturn(expectedUser);

        Exception e = assertThrows(Exception.class, ()->{
            User result = this.userService.deleteUser(0);
        });

        assertEquals("id not valid", e.getMessage());
    }

    @Test
    public void shouldNotDeleteUserGivenNonexistentId() throws Exception {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setFirstname("unit");
        expectedUser.setLastname("test");

        when(this.userRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(this.userRepository.deleteById(1)).thenReturn(expectedUser);

        UserServiceException e = assertThrows(UserServiceException.class, ()->{
            User result = this.userService.deleteUser(2);
        });

        assertEquals("user not found by given id", e.getMessage());
    }
}