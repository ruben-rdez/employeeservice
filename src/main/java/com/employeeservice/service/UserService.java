package com.employeeservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employeeservice.entity.UserEntity;
import com.employeeservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
