package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "{userID}")
    public ResponseEntity<User> getUser(@PathVariable("userID") Long userId){
        return userService.getUser(userId);
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody User user){
        return userService.addNewUser(user);
    }

    @PutMapping(path = "{userID}")
    public ResponseEntity updateUser(
            @PathVariable("userID") Long userID,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer addressID,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email
    ){
        return userService.updateUser(userID,firstName,lastName,addressID,phoneNumber,email);
    }

    @DeleteMapping(path = "{userID}")
    public ResponseEntity deleteUserAccount(@PathVariable("userID") Long userId){
        return userService.deleteUser(userId);
    }


}
