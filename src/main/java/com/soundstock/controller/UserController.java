package com.soundstock.controller;

import com.soundstock.model.dto.UserDTO;
import com.soundstock.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        userService.registerUser(userDTO, response);
    }

    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmUser(HttpServletRequest request) {
        userService.confirmUser(request.getHeader("token"));
    }

    @GetMapping("/jwt")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String securedEndpoint() {
        return "jwt secured";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        userService.loginWithJWT(userDTO, response);
    }

    @GetMapping("/userlist")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return userService.refreshToken(request, response);
    }

}

