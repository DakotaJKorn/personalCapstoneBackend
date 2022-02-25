package com.example.demo.UserLogin;

import com.example.demo.User.User;
import com.example.demo.UserAccounts.UserAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "userlogin")
public class UserLoginController {

    private final UserLoginService userLoginService;

    @Autowired
    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @GetMapping
    public List<UserLogin> getUserLogins(){
        return userLoginService.getUserLogins();
    }

    @GetMapping(path = "{userEmail}")
    public UserLogin getUserLogin(@PathVariable("userEmail") String userEmail){
        return userLoginService.getUserLogin(userEmail);
    }

    @PostMapping
    public void createUserLogin(UserLogin userlogin){
        userLoginService.createUserLogin(userlogin);
    }

    @DeleteMapping(path = "{userEmail}")
    public void deleteUserLogin(@PathVariable("userEmail") String email){
        userLoginService.deleteUserLogin(email);
    }

    @PutMapping(path = "{userEmail}")
    public void updateUserAccount(
            @PathVariable("userEmail") String userEmail,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password
    ){
        userLoginService.updateUserLogin(userEmail, email, password);
    }

    @GetMapping(path = "loginAttempt")
    public User loginAttempt(@RequestParam(required = true) String email,
                             @RequestParam(required = true) String password) {

        UserLogin userLogin = new UserLogin(email,password);
        return userLoginService.loginAttempt(userLogin);
    }
}
