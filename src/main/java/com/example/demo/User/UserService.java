package com.example.demo.User;

import com.example.demo.UserAccounts.UserAccounts;
import com.example.demo.UserAccounts.UserAccountsRepository;
import com.example.demo.UserLogin.UserLogin;
import com.example.demo.UserLogin.UserLoginRepository;
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
        userAccountsRepository.save(userAccounts);

    }

    @Transactional
    public void updateUser(Long userID, String firstName, String lastName, Integer addressID, String phoneNumber, String email) {
       User user = userRepository.findById(userID).orElseThrow(() -> new IllegalStateException("user account with id does not exist"));

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
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if(!exists){
            throw new IllegalStateException("User account with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }
}
