package com.soundstock.controller;

import com.soundstock.mapper.UserMapper;
import com.soundstock.model.User;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/v1")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public String confirmUser(@RequestParam("token") String token) {
        userService.confirmUser(token);
        return "User verified successfully";
    }

    @GetMapping("/jwt")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String securedEndpoint() {
        return "jwt secured";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        return userService.loginWithJWT(userDTO, response);
    }

    @GetMapping("/userlist")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return userMapper.mapToUserDTOList(userList);
    }
}

