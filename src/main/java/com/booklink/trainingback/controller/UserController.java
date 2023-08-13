package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserResponseDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto registerUser(@RequestBody CreateUserDto userDto) {
        return this.userService.registerUser(userDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id, @RequestParam String template) {
        //secured
        if (template.equals("full")) {
            return this.userService.getUserWithPassword(id);
        } else if (template.equals("basic")) {
            return this.userService.getUser(id);
        }
        throw new NotFoundException("Template %s not found".formatted(id));
    }

    @GetMapping()
    public List<UserResponseDto> getAllUsers() {
        return this.userService.getAllUsers();
    }
}