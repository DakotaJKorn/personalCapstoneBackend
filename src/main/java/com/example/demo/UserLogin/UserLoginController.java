package com.example.demo.UserLogin;

import com.example.demo.User.User;
import com.example.demo.UserAccounts.UserAccounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserLogin>> getUserLogins(){
        return userLoginService.getUserLogins();
    }

    @GetMapping(path = "{userEmail}")
    public ResponseEntity<UserLogin> getUserLogin(@PathVariable("userEmail") String userEmail){
        return userLoginService.getUserLogin(userEmail);
    }

    @PostMapping
    public ResponseEntity createUserLogin(@RequestParam(required = true) String email,
                                          @RequestParam(required = true) String password){
        UserLogin userLogin = new UserLogin(email,password);
        return userLoginService.createUserLogin(userLogin);
    }

    @DeleteMapping(path = "{userEmail}")
    public ResponseEntity deleteUserLogin(@PathVariable("userEmail") String email){
        return userLoginService.deleteUserLogin(email);
    }

    @PutMapping(path = "{userEmail}")
    public ResponseEntity updateUserAccount(
            @PathVariable("userEmail") String userEmail,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password
    ){
        return userLoginService.updateUserLogin(userEmail, email, password);
    }

    @GetMapping(path = "loginAttempt")
    public ResponseEntity<User> loginAttempt(@RequestParam(required = true) String email,
                                             @RequestParam(required = true) String password) {

        UserLogin userLogin = new UserLogin(email,password);
        return userLoginService.loginAttempt(userLogin);
    }
}
