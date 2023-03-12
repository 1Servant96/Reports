package com.example.report.repo;

import com.example.report.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name = :email")
    Optional<User> findByEmail(String email);

}