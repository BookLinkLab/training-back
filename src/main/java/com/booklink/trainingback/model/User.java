package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.CreateUserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ACCOUNT")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public static User from(CreateUserDto userDto, String encryptedPassword) {
        return User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(encryptedPassword)
                .build();
    }
}