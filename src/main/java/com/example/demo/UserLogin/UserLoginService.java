package com.example.demo.UserLogin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    public UserLoginService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public List<UserLogin> getUserLogins() {
        return userLoginRepository.findAll();
    }

    public UserLogin getUserLogin(String email){
        return userLoginRepository.findUserLoginByEmail(email).orElseThrow(() -> new IllegalStateException("user login with id does not exist"));
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
}
