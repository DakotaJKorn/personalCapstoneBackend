package com.example.demo.UserLogin;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public  ResponseEntity<List<UserLogin>> getUserLogins() {
        return ResponseEntity.ok(userLoginRepository.findAll());
    }

    public ResponseEntity<UserLogin> getUserLogin(String email){
        Optional<UserLogin> userLoginOptional = userLoginRepository.findUserLoginByEmail(email);

        if(userLoginOptional.isPresent()){
            return ResponseEntity.ok(userLoginOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public ResponseEntity createUserLogin(UserLogin userlogin) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(userlogin.getEmail());

        if(optionalUserLogin.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        userLoginRepository.save(userlogin);
        return null;
    }

    public ResponseEntity deleteUserLogin(String email) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(email);

        if(!optionalUserLogin.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        userLoginRepository.delete(optionalUserLogin.get());
        return ResponseEntity.ok(null);
    }


    @Transactional
    public ResponseEntity updateUserLogin(String userEmail, String email, String password) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(userEmail);

        if(!optionalUserLogin.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        UserLogin userLogin = optionalUserLogin.get();

        if(password != null && password.length() > 0 && !Objects.equals(userLogin.getPassword(),password)){
            userLogin.setPassword(password);
        }
        if(email != null && email.length() > 0 && !Objects.equals(userLogin.getEmail(),email)){
            Optional<UserLogin> userLoginOptional = userLoginRepository.findUserLoginByEmail(email);
            if(userLoginOptional.isPresent()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            userLogin.setEmail(email);
        }
        return ResponseEntity.ok(null);
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
