package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer > {

    @Override
    Optional<User> findById(Integer integer);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);
}
