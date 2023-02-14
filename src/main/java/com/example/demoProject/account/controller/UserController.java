package com.example.demoProject.account.controller;

import com.example.demoProject.account.dto.UserDto;
import com.example.demoProject.account.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {

        return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Page<UserDto>> list(@ApiIgnore Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);

    }
}
