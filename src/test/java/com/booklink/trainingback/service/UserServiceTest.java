package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserResponseDto;
import com.booklink.trainingback.exception.BookAlreadyExistsException;
import com.booklink.trainingback.exception.SpecialCharacterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void happyPathTest() {
        assertTrue(this.userService.getAllUsers().isEmpty());

        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("setup")
                .email("setup@spring.com")
                .password("setup")
                .build();
        UserDto savedUser = this.userService.registerUser(createUserDto);

        List<UserResponseDto> allUsers = this.userService.getAllUsers();
        assertFalse(allUsers.isEmpty());
        assertEquals(1, allUsers.size());

        UserResponseDto myUser = allUsers.get(0);
        assertEquals(savedUser, myUser.getUserDto());
    }

    @Test
    void exceptionTest() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("setup")
                .email("setup@spring.com")
                .password("$")
                .build();
        assertThrows(SpecialCharacterException.class, () -> this.userService.registerUser(createUserDto));
    }
}
