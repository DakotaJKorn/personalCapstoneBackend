package com.example.demo.User;

import com.example.demo.UserLogin.UserLogin;
import com.example.demo.UserLogin.UserLoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    public UserService(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }

    public List<User> getUsers(){ return userRepository.findAll(); }

    public Optional<User> findStudentByPhoneNumber(String phoneNumber){
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(phoneNumber);
        if(!userOptional.isPresent()){
            throw new IllegalStateException("User could not be found");
        }
        return userOptional;
    }

    public void addNewUser(User user){
        Optional<UserLogin> emailExists = userLoginRepository.findUserLoginByEmail(user.getEmail());

        if(emailExists.isPresent()){
            throw new IllegalStateException("Email is already registered with a user.");
        }
        userRepository.save(user);

        UserLogin userLogin = new UserLogin(user.getEmail(), user.getFirstName()+user.getLastName());
        userLoginRepository.save(userLogin);

    }
}
