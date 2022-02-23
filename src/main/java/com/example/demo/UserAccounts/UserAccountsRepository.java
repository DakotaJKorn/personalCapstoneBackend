package com.example.demo.UserAccounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

}
