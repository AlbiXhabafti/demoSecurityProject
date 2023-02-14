package com.example.demoProject.account.service;


import com.example.demoProject.account.dto.LoginDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);

}