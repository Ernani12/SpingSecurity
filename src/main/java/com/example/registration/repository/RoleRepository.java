package com.example.registration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.registration.entity.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
}
