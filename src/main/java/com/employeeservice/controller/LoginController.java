package com.employeeservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = {"/home",""})
public class LoginController {

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping(value = {"/login","/",""})
    public String handleLogin(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, 
        HttpServletResponse response) {
        logoutHandler.logout(request, response, authentication);    
        return "login";
    }
}
