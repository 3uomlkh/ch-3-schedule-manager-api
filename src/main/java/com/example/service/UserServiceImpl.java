package com.example.service;

import com.example.dto.user.UserRequestDto;
import com.example.dto.user.UserResponseDto;
import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getPassword());
        return userRepository.saveUser(user);
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return List.of();
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserResponseDto findUserByNameAndPassword(User user) {
        return null;
    }

    @Override
    public UserResponseDto findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto dto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId, String password) {

    }
}
