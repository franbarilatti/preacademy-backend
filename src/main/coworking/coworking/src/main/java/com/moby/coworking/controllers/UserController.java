package com.moby.coworking.controllers;


import com.moby.coworking.dtos.UserDTO;
import com.moby.coworking.models.User;
import com.moby.coworking.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
