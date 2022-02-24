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

    public UserLogin getUserLogin(Long id){
        return userLoginRepository.findById(id).orElseThrow(() -> new IllegalStateException("user login with id does not exist"));
    }

    public void createUserLogin(UserLogin userlogin) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findUserLoginByEmail(userlogin.getEmail());

        if(optionalUserLogin.isPresent()){
            throw new IllegalStateException("email already exists");
        }

        userLoginRepository.save(userlogin);
    }

    public void deleteUserLogin(Long id) {
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findById(id);

        if(!optionalUserLogin.isPresent()){
            throw new IllegalStateException("user login does not exist");
        }

        userLoginRepository.deleteById(id);
    }


    @Transactional
    public void updateUserLogin(Long userLoginID, String email, String password) {
        UserLogin userLogin = userLoginRepository.findById(userLoginID).orElseThrow(() -> new IllegalStateException("user login with id does not exist"));

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
