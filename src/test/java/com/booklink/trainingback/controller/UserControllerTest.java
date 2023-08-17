package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/user";

    @BeforeEach
    void setup() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("setup")
                .email("setup@spring.com")
                .password("setup")
                .build();
        this.restTemplate.postForEntity(this.baseUrl, createUserDto, CreateUserDto.class);
    }

    @Test
    void registerUser() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("test")
                .email("springsecurity@spring.com")
                .password("test")
                .build();
        ResponseEntity<UserDto> response = this.restTemplate.exchange(this.baseUrl, HttpMethod.POST, new HttpEntity<>(createUserDto), UserDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        UserDto responseUser = UserDto.builder()
                .id(2L)
                .username("test")
                .email("springsecurity@spring.com")
                .build();

        assertEquals(response.getBody(), responseUser);
    }

    @Test
    void registerUserSpecialCharacterException() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username("test")
                .email("springsecurity@spring.com")
                .password("$")
                .build();
        ResponseEntity<UserDto> response = this.restTemplate.exchange(this.baseUrl, HttpMethod.POST, new HttpEntity<>(createUserDto), UserDto.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getUser() {
        ResponseEntity<UserResponseDto> response = this.restTemplate.exchange(
                this.baseUrl + "/1?template=full", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).getUserWithPasswordDto().getPassword().contains("$"));
    }

    @Test
    void getAllUsers() {
        ResponseEntity<List<UserResponseDto>> responseBasic = this.restTemplate.exchange(
                this.baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, responseBasic.getStatusCode());
    }
}
