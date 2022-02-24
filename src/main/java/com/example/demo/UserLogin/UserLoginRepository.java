package com.example.demo.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    @Query("SELECT u FROM UserLogin u WHERE u.email = ?1")
    Optional<UserLogin> findUserLoginByEmail(String email);

    @Query("DELETE u FROM UserLogin u WHERE u.email = ?1")
    void deleteByEmail(String email);
}
