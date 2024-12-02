package com.employeeservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employeeservice.entity.UserEntity;
import com.employeeservice.exception.UserNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userService.findByUsername(username);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            return User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles()
                        .build();
        } else {
            throw new UserNotFoundException("User not found");
        }
     }
}
