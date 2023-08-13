package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserDtoWithPassword;
import com.booklink.trainingback.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto registerUser(@RequestBody CreateUserDto userDto) {
        return this.userService.registerUser(userDto);
    }

    @GetMapping("/{id}")
    public UserDtoWithPassword getUser(@PathVariable Long id) {
        return this.userService.getUserFull(id);
    }
}