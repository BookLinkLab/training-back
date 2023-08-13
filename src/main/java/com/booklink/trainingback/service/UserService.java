package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;

public interface UserService {
    UserDto registerUser(CreateUserDto userDto);
}