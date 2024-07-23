package com.example.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registration.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
