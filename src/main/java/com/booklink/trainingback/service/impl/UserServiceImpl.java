package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.CreateUserDto;
import com.booklink.trainingback.dto.UserDto;
import com.booklink.trainingback.dto.UserDtoWithPassword;
import com.booklink.trainingback.dto.UserResponse;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.exception.SpecialCharacterException;
import com.booklink.trainingback.model.User;
import com.booklink.trainingback.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements com.booklink.trainingback.service.UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(CreateUserDto userDto) {
        if (userDto.getPassword().contains("$"))
            throw new SpecialCharacterException();
        String encryptedPassword = this.passwordEncoder.encode(userDto.getPassword());
        User userToSave = User.from(userDto, encryptedPassword);
        User savedUser = this.userRepository.save(userToSave);
        return UserDto.from(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new NotFoundException("User %d not found".formatted(id)));
        return UserResponse.builder().userDto(UserDto.from(user)).build();
    }

    @Override
    public UserResponse getUserWithPassword(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.orElseThrow(() -> new NotFoundException("User %d not found".formatted(id)));
        return UserResponse.builder().userDtoWithPassword(UserDtoWithPassword.from(user)).build();
    }
}