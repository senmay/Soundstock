package com.soundstock.controller;

import com.soundstock.model.dto.UserDTO;
import com.soundstock.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/v1")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
    }

    @GetMapping("/confirm")
    public void confirmUser(@RequestParam("token") String token) {
        userService.confirmUser(token);
    }
}
