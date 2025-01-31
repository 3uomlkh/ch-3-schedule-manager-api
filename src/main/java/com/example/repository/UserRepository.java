package com.example.repository;

import com.example.dto.user.UserRequestDto;
import com.example.dto.user.UserResponseDto;
import com.example.entity.User;

import java.util.List;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    UserResponseDto findUserByEmail(String email);
    UserResponseDto findUserByNameAndPassword(String name, String password);
    List<UserResponseDto> findAllUsers();
    UserResponseDto findUserByNameAndPassword(User user);
    UserResponseDto findUserById(Long userId);
    UserResponseDto updateUser(Long userId, UserRequestDto dto);
    void deleteUser(Long userId, String password);
}
