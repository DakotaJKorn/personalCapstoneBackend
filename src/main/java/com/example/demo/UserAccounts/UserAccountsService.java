package com.example.demo.UserAccounts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAccountsService {

    private UserAccountsRepository userAccountsRepository;

    public UserAccountsService(UserAccountsRepository userAccountsRepository) {
        this.userAccountsRepository = userAccountsRepository;
    }

    public ResponseEntity<List<UserAccounts>> getUserAccounts() {
        return ResponseEntity.ok(userAccountsRepository.findAll());
    }

    public ResponseEntity<UserAccounts> getUserAccount(Long userAccountID){
        Optional<UserAccounts> userAccount = userAccountsRepository.findById(userAccountID);

        if(!userAccount.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(userAccount.get());
    }

    @Transactional
    public ResponseEntity updateUserAccount(Long userAccountID, Long accountTotal, Long portfolioTotal) {
        Optional<UserAccounts> optionalUserAccounts = userAccountsRepository.findById(userAccountID);

        if(!optionalUserAccounts.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserAccounts userAccounts = optionalUserAccounts.get();

        if(accountTotal != null && !Objects.equals(userAccounts.getAccountsTotal(),accountTotal)){
            userAccounts.setAccountsTotal(accountTotal);
        }
        if(portfolioTotal != null && !Objects.equals(userAccounts.getPortfolioTotal(),portfolioTotal)){
            userAccounts.setPortfolioTotal(portfolioTotal);
        }

        return ResponseEntity.ok(null);
    }

    public ResponseEntity deleteUserAccount(Long userAccountsId) {
        boolean exists = userAccountsRepository.existsById(userAccountsId);

        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userAccountsRepository.deleteById(userAccountsId);

        return ResponseEntity.ok(null);
    }

    public ResponseEntity addNewUserAccount(UserAccounts userAccount) {
        userAccountsRepository.save(userAccount);
        return ResponseEntity.ok(null);
    }


}
