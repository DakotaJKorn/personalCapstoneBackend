package com.example.demo.UserAccounts;

import com.example.demo.student.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAccountsService {

    private UserAccountsRepository userAccountsRepository;

    public UserAccountsService(){}

    public UserAccountsService(UserAccountsRepository userAccountsRepository) {
        this.userAccountsRepository = userAccountsRepository;
    }


    public List<UserAccounts> getUserAccounts() {
        return userAccountsRepository.findAll();
    }

    public Optional<UserAccounts> getUserAccount(Long userAccountID){
        Optional<UserAccounts> optionalUserAccounts = userAccountsRepository.findById(userAccountID);

        if(!optionalUserAccounts.isPresent()){
            throw new IllegalStateException("User account with id " + userAccountID + " does not exist");
        }

        return  optionalUserAccounts;
    }

    @Transactional
    public void updateUserAccount(Long userAccountID, Long accountTotal, Long portfolioTotal) {
        UserAccounts userAccounts = userAccountsRepository.findById(userAccountID).orElseThrow(() -> new IllegalStateException("user account with id does not exist"));

        if(accountTotal != null && !Objects.equals(userAccounts.getAccountsTotal(),accountTotal)){
            userAccounts.setAccountsTotal(accountTotal);
        }
        if(portfolioTotal != null && !Objects.equals(userAccounts.getPortfolioTotal(),portfolioTotal)){
            userAccounts.setPortfolioTotal(portfolioTotal);
        }
    }

    public void deleteUserAccount(Long userAccountsId) {
        boolean exists = userAccountsRepository.existsById(userAccountsId);

        if(!exists){
            throw new IllegalStateException("User account with id " + userAccountsId + " does not exist");
        }
        userAccountsRepository.deleteById(userAccountsId);
    }

    public void addNewUserAccount(UserAccounts userAccount) {
        userAccountsRepository.save(userAccount);
    }


}
