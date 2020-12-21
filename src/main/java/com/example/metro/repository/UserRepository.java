package com.example.metro.repository;

import com.example.metro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByLogin(String login);
}
