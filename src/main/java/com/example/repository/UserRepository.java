package com.example.repository;

import com.example.dto.user.UserResponseDto;
import com.example.entity.Schedule;
import com.example.entity.User;

import java.util.List;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    UserResponseDto findUserByEmail(String email);
    User findUserByIdWithPassword(Long userId);
    User findUserByNameAndPassword(String name, String password);
    UserResponseDto findUserById(Long userId);
    int updateUser(Long userId, User user);
    int deleteUser(Long userId);
}
