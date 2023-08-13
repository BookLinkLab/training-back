package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(CreateUserDto userDto);
    UserResponseDto getUser(Long id);
    UserResponseDto getUserWithPassword(Long id);
    List<UserResponseDto> getAllUsers();
}