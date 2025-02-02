package com.example.controller;

import com.example.dto.user.UserRequestDto;
import com.example.dto.user.UserResponseDto;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto dto) {
        return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<UserResponseDto> findUserByEmail(@RequestParam String email) {
        if (email == null) {
            // TODO : 예외처리
        }
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserResponseDto> findUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findUserById(userId), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequestDto dto
    ) {
        return new ResponseEntity<>(userService.updateUser(userId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId,
            @RequestBody UserRequestDto dto
    ) {
        userService.deleteUser(userId, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
