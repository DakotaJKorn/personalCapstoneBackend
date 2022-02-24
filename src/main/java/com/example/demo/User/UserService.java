package com.example.demo.User;

import com.example.demo.UserAccounts.UserAccounts;
import com.example.demo.UserAccounts.UserAccountsRepository;
import com.example.demo.UserAccounts.UserAccountsService;
import com.example.demo.UserLogin.UserLogin;
import com.example.demo.UserLogin.UserLoginRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final UserAccountsService userAccountsService;

    public UserService(UserRepository userRepository, UserLoginRepository userLoginRepository, UserAccountsService userAccountsService) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
        this.userAccountsService = userAccountsService;
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
        Optional<User> phoneNumberExists = userRepository.findUserByPhoneNumber(user.getPhoneNumber());

        if(emailExists.isPresent()){
            throw new IllegalStateException("Email is already registered with a user.");
        }

        userRepository.save(user);

        UserLogin userLogin = new UserLogin(user.getEmail(), user.getFirstName()+user.getLastName());
        userLoginRepository.save(userLogin);

        UserAccounts userAccounts = new UserAccounts(user.getId(), 0L , 0L);
        userAccountsService.addNewUserAccount(userAccounts);

    }
}
