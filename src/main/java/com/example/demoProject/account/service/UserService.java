package com.example.demoProject.account.service;

import com.example.demoProject.account.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    String register(UserDto registerDto);

    Page<UserDto> findAll(Pageable pageable);
}
