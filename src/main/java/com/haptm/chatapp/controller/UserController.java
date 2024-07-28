package com.haptm.chatapp.controller;

import com.haptm.chatapp.dto.PasswordDto;
import com.haptm.chatapp.dto.SignupDto;
import com.haptm.chatapp.dto.UpdateDto;
import com.haptm.chatapp.dto.UserDto;
import com.haptm.chatapp.model.User;
import com.haptm.chatapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Tag(
        name = "CRUD REST APIs for User Resource",
        description = "CRUD REST APIs - Create User, Update User, Get User, Get All Users, Delete User"
)
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody SignupDto signupDto) {
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User user = modelMapper.map(signupDto, User.class);
        userService.createUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(
            summary = "Get User By ID REST API",
            description = "Get User By ID REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserDto> getUserById(@Valid @PathVariable Long userId) {
        User user = userService.getUserById(userId);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get User By Username REST API",
            description = "Get User By Username REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@Valid @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get User By Email REST API",
            description = "Get User By Email REST API is used to get a single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/email")
    public ResponseEntity<UserDto> getUserByEmail(@Valid @RequestBody String email) {
        User user = userService.getUserByEmail(email);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Get All Friends Of A User Email REST API",
            description = "Get all friends of a user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping("/{userId}/friends")
    public ResponseEntity<Set<UserDto>> getFriends(@Valid @PathVariable Long userId) {
        Set<User> users = userService.getAllFriends(userId);
        Set<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toSet());
        return userDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(userDtos);
    }

    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update a particular user in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/information")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable Long userId, @Valid @RequestBody UpdateDto updateDto) {
        User user = modelMapper.map(updateDto, User.class);
        UserDto userResponse = modelMapper.map(userService.updateUser(userId, user), UserDto.class);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update user's password in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/password")
    public ResponseEntity<UserDto> changePassword(@Valid @PathVariable Long userId, @Valid @RequestBody PasswordDto passwordDto) {
        User user = userService.updatePassword(userId, passwordEncoder.encode(passwordDto.getCurrentPassword()), passwordEncoder.encode(passwordDto.getNewPassword()));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @Operation(
            summary = "Delete friend REST API",
            description = "Delete friend of a User REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @PutMapping("/{userId}/friend/{friendId}")
    public ResponseEntity<String> deleteFriend(@Valid @PathVariable Long userId, @Valid @PathVariable Long friendId) {
        userService.deleteFriend(userId, friendId);
        return ResponseEntity.ok("Friend deleted successfully");
    }

    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@Valid @PathVariable Long id, @Valid @RequestBody String password) {
        userService.deleteUser(id, passwordEncoder.encode(password));
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
