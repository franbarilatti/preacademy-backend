package com.moby.coworking.services;

import com.moby.coworking.dtos.UserDTO;
import com.moby.coworking.models.Role;
import com.moby.coworking.models.User;
import com.moby.coworking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO){
        User user = new User();

        user.setFullName(userDTO.getFullName());
        user.setEmail(user.getEmail());
        user.setRole(Role.valueOf(userDTO.getRole()));

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    }



}
