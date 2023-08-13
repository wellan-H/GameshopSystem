package com.wellan.gameshopsystem.controller;

import com.wellan.gameshopsystem.dto.UserRequest;
import com.wellan.gameshopsystem.model.User;
import com.wellan.gameshopsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //registry
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest){
        Integer userId = userService.register(userRequest);
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserRequest userRequest){
        User user = userService.login(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }
}
