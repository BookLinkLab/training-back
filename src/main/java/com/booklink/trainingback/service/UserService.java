package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserResponse;

public interface UserService {
    UserDto registerUser(CreateUserDto userDto);
    UserResponse getUser(Long id);
    UserResponse getUserWithPassword(Long id);
}