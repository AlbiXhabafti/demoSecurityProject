package com.example.demoProject.account.service.impl;

import com.example.demoProject.account.dto.UserDto;
import com.example.demoProject.account.model.Role;
import com.example.demoProject.account.model.User;
import com.example.demoProject.account.repository.RoleRepository;
import com.example.demoProject.account.repository.UserRepository;
import com.example.demoProject.account.service.UserService;
import com.example.demoProject.exception.HerAPIException;
import jakarta.transaction.Transactional;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public String register(UserDto registerDto) {
      if (userRepository.existsByUsername(registerDto.getUsername())){
          throw new HerAPIException(HttpStatus.BAD_REQUEST,"Username is already exists!");
      }
      if (userRepository.existsByEmail(registerDto.getEmail())){
          throw new HerAPIException(HttpStatus.BAD_REQUEST,"Email is already exits!");
      }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Optional<Role> role = roleRepository.findByName("ADMIN");
        user.setRole(role.isPresent() ? role.get() : null);
        userRepository.save(user);

        return "User registered successfully!.";

    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> userList = userRepository.findAll(pageable);
        val userPage = userList.map(e -> mapper.map(e, UserDto.class));

        return userPage;
    }
}
