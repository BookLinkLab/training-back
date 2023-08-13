package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserDtoWithPassword;
import com.booklink.trainingback.dto.UserResponse;
import com.booklink.trainingback.exception.NotFoundException;
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
    public UserResponse getUser(@PathVariable Long id, @RequestParam String template) {
        //secured
        if (template.equals("full")) {
            return this.userService.getUserWithPassword(id);
        } else if (template.equals("basic")) {
            return this.userService.getUser(id);
        }
        throw new NotFoundException("Template %s not found".formatted(id));
    }
}