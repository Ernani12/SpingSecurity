package com.example.registration.service;

import java.util.List;

import com.example.registration.dto.UserDto;
import com.example.registration.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
