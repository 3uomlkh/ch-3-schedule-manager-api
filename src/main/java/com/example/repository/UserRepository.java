package com.example.repository;

import com.example.dto.user.UserResponseDto;
import com.example.entity.Schedule;
import com.example.entity.User;

import java.util.List;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    UserResponseDto findUserByEmail(String email);
    User findUserByIdWithPassword(Long userId);
    UserResponseDto findUserByNameAndPassword(String name, String password);
    List<UserResponseDto> findAllUsers();
    UserResponseDto findUserByNameAndPassword(User user);
    UserResponseDto findUserById(Long userId);
    int updateUser(Long userId, User user);
    int deleteUser(Long userId);
}
