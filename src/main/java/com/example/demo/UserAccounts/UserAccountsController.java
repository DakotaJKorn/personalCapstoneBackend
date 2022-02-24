package com.example.demo.UserAccounts;

import com.example.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserAccounts> getUserAccounts(){
        return userAccountsService.getUserAccounts();
    }

    @PostMapping
    public void registerNewStudent(UserAccounts userAccount){
        userAccountsService.addNewUserAccount(userAccount);
    }

    @DeleteMapping(path = "{userAccountsID}")
    public void deleteUserAccount(@PathVariable("userAccountsID") Long userAccountsId){
        userAccountsService.deleteUserAccount(userAccountsId);
    }

    @PutMapping(path = "{userAccountsID}")
    public void updateUserAccount(
            @PathVariable("userAccountsID") Long userAccountsID,
            @RequestParam(required = false) Long accountTotal,
            @RequestParam(required = false) Long portfolioTotal
    ){
        userAccountsService.updateUserAccount(userAccountsID, accountTotal, portfolioTotal);
    }

}
