package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/user";

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
                .username("test")
                .email("springsecurity@spring.com")
                .build();

        assertEquals(response.getBody(), responseUser);
    }
}
