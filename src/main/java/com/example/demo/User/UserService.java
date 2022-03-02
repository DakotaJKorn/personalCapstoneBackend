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

    public List<User> getUsers(){ return userRepository.findAll(); }

    public User getUser(Long userId) {
        return userRepository.getById(userId);
    }


    public ResponseEntity addNewUser(User user){
        Optional<UserLogin> emailExists = userLoginRepository.findUserLoginByEmail(user.getEmail());
        Optional<User> phoneNumberExists = userRepository.findUserByPhoneNumber(user.getPhoneNumber());

        if(emailExists.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        userRepository.save(user);

        UserLogin userLogin = new UserLogin(user.getEmail(), user.getFirstName()+user.getLastName());
        userLoginRepository.save(userLogin);

        UserAccounts userAccounts = new UserAccounts(user.getId(), 0L , 0L);
        userAccountsRepository.save(userAccounts);

        return null;
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

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if(!exists){
            throw new IllegalStateException("User account with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }
}
