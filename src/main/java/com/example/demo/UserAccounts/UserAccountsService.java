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

    public UserAccountsService(UserAccountsRepository userAccountsRepository) {
        this.userAccountsRepository = userAccountsRepository;
    }

    public List<UserAccounts> getUserAccounts() {
        return userAccountsRepository.findAll();
    }

    public UserAccounts getUserAccount(Long userAccountID){
        UserAccounts UserAccount = userAccountsRepository.findById(userAccountID).orElseThrow(() -> new IllegalStateException("user account with id does not exist"));

        return  UserAccount;
    }

    @Transactional
    public void updateUserAccount(Long userAccountID, Long accountTotal, Long portfolioTotal) {
        UserAccounts userAccounts = userAccountsRepository.findById(userAccountID).orElseThrow(() -> new IllegalStateException("user account with id does not exist"));

        System.out.println("Account Total: " + accountTotal + " Portfolio Total: " + portfolioTotal);
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
