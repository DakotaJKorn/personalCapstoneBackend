package com.example.demo.UserLogin;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;

    public UserLoginService(UserLoginRepository userLoginRepository, UserRepository userRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userRepository = userRepository;
    }

    public List<UserLogin> getUserLogins() {
        return userLoginRepository.findAll();
    }

    public ResponseEntity<UserLogin> getUserLogin(String email){
        //return userLoginRepository.findUserLoginByEmail(email).orElseThrow(() -> new NoSuchElementException("user login with id does not exist"));

        Optional<UserLogin> userLoginOptional = userLoginRepository.findUserLoginByEmail(email);

        if(userLoginOptional.isPresent()){
            return ResponseEntity.ok(userLoginOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public void createUserLogin(UserLogin userlogin) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(userlogin.getEmail());

        if(optionalUserLogin.isPresent()){
            throw new IllegalStateException("email already exists");
        }

        userLoginRepository.save(userlogin);
    }

    public void deleteUserLogin(String email) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(email);

        if(!optionalUserLogin.isPresent()){
            throw new IllegalStateException("user login does not exist");
        }
        UserLogin toDelete = optionalUserLogin.get();
        userLoginRepository.delete(toDelete);
    }


    @Transactional
    public void updateUserLogin(String userEmail, String email, String password) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(userEmail);

        if(!optionalUserLogin.isPresent()){
            throw new IllegalStateException("user login does not exist");
        }

        UserLogin userLogin = optionalUserLogin.get();

        if(password != null && password.length() > 0 && !Objects.equals(userLogin.getPassword(),password)){
            userLogin.setPassword(password);
        }
        if(email != null && email.length() > 0 && !Objects.equals(userLogin.getEmail(),email)){
            Optional<UserLogin> userLoginOptional = userLoginRepository.findUserLoginByEmail(email);
            if(userLoginOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            userLogin.setEmail(email);
        }
    }

    public ResponseEntity<User> loginAttempt(UserLogin userLogin) {

        Optional<UserLogin> userLoginOptional = userLoginRepository.findUserLoginByEmail(userLogin.getEmail());

        if(!userLoginOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if(!Objects.equals(userLogin.getPassword(), userLoginOptional.get().getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(userRepository.findByEmail(userLogin.getEmail()));

    }
}
