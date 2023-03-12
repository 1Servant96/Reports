package com.example.report.repo;

import com.example.report.dto.UserResponse;
import com.example.report.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name = :email")
    Optional<User> findByEmail(String email);

    @Query("select new com.example.report.dto.UserResponse(u.id, u.name, u.email, u.image) from User u where u.id = :userId")
    UserResponse getUserById(Long userId);

}