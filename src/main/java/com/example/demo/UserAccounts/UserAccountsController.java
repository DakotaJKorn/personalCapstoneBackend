package com.example.demo.UserAccounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "useraccounts")
public class UserAccountsController {

    private final UserAccountsService userAccountsService;

    @Autowired
    public UserAccountsController(UserAccountsService userAccountsService) {
        this.userAccountsService = userAccountsService;
    }

    @GetMapping
    public ResponseEntity<List<UserAccounts>> getUserAccounts(){
        return userAccountsService.getUserAccounts();
    }

    @GetMapping(path = "{userAccountID}")
    public ResponseEntity<UserAccounts> getUserAccount(@PathVariable("userAccountID") Long userAccountId){
        return userAccountsService.getUserAccount(userAccountId);
    }

    @PostMapping
    public ResponseEntity registerNewStudent(UserAccounts userAccount){
        return userAccountsService.addNewUserAccount(userAccount);
    }

    @DeleteMapping(path = "{userAccountsID}")
    public ResponseEntity deleteUserAccount(@PathVariable("userAccountsID") Long userAccountsId){
        return userAccountsService.deleteUserAccount(userAccountsId);
    }

    @PutMapping(path = "{userAccountsID}")
    public ResponseEntity updateUserAccount(
            @PathVariable("userAccountsID") Long userAccountsID,
            @RequestParam(required = false) Long accountTotal,
            @RequestParam(required = false) Long portfolioTotal
    ){
        return userAccountsService.updateUserAccount(userAccountsID, accountTotal, portfolioTotal);
    }

}
