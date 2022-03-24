package com.example.demo.User;

import com.example.demo.UserAccounts.UserAccounts;
import com.example.demo.UserAccounts.UserAccountsRepository;
import com.example.demo.UserLogin.UserLogin;
import com.example.demo.UserLogin.UserLoginRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserAccountsRepository userAccountsRepository;

    public UserService(UserRepository userRepository, UserLoginRepository userLoginRepository, UserAccountsRepository userAccountsRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userAccountsRepository = userAccountsRepository;
    }

    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<User> getUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(userOptional.get());
    }


    public ResponseEntity addNewUser(UserLogin userLogin, User user){

        System.out.println("________________________________________________________________________________");
        System.out.println("MADE IT HERE");
        System.out.println("________________________________________________________________________________");
        System.out.println(userLogin.toString());
        System.out.println("________________________________________________________________________________");
        System.out.println(user.toString());
        System.out.println("________________________________________________________________________________");

        Optional<UserLogin> emailExists = userLoginRepository.findUserLoginByEmail(user.getEmail());

        if(emailExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        userRepository.save(user);

        System.out.println("________________________________________________________________________________");
        System.out.println("SAVED USER");
        System.out.println("________________________________________________________________________________");

        userLoginRepository.save(userLogin);
        System.out.println("________________________________________________________________________________");
        System.out.println("SAVED USER LOGIN");
        System.out.println("________________________________________________________________________________");


        UserAccounts userAccounts = new UserAccounts(user.getId(), 200000L , 600000L);
        userAccountsRepository.save(userAccounts);
        System.out.println("________________________________________________________________________________");
        System.out.println("SAVED USER ACCOUNTS" + user.getId());
        System.out.println("________________________________________________________________________________");

        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity updateUser(Long userID, String firstName, String lastName, Integer addressID, String phoneNumber, String email) {
        Optional<User> userOptional = userRepository.findById(userID);

        if(!userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOptional.get();
        Optional<UserLogin> emailExists = userLoginRepository.findUserLoginByEmail(email);

        if(emailExists.isPresent() && !Objects.equals(user.getEmail(),email)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        if(firstName != null && !Objects.equals(user.getFirstName(),firstName)){
            user.setFirstName(firstName);
        }
        if(lastName != null && !Objects.equals(user.getLastName(),lastName)){
            user.setLastName(lastName);
        }
        if(addressID != null && !Objects.equals(user.getAddressID(),addressID)){
            user.setAddressID(addressID);
        }
        if(phoneNumber != null && !Objects.equals(user.getPhoneNumber(),phoneNumber)){
            user.setPhoneNumber(phoneNumber);
        }
        if(email != null && !Objects.equals(user.getEmail(),email)){
            user.setEmail(email);
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if(!exists){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok(null);
    }
}
