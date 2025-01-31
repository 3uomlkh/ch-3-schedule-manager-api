package com.example.service;

import com.example.dto.user.UserRequestDto;
import com.example.dto.user.UserResponseDto;
import com.example.entity.User;

import java.util.List;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto dto);
    List<UserResponseDto> findAllUsers();
    UserResponseDto findUserByEmail(String email);
    UserResponseDto findUserByNameAndPassword(User user);
    UserResponseDto findUserById(Long userId);
    UserResponseDto updateUser(Long userId, UserRequestDto dto);
    void deleteUser(Long userId, String password);
}
