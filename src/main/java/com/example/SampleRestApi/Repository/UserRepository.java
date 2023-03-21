package com.example.SampleRestApi.Repository;

import com.example.SampleRestApi.models.SQL.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsernameOrEmail(String username, String email);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
