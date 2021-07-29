package com.example.demo.controller;

import com.example.demo.entities.UserEntity;
import com.example.demo.service.UserService;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GreetingController {
    private  final UserService userService;

    public GreetingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("success");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return userService.AllUsers();
    }

    @PostMapping("/post/auth")
    public ResponseEntity<String> auth(@RequestParam final String email, @RequestParam final String password) {
        return userService.createUser(new UserEntity(email,password));
    }
}
