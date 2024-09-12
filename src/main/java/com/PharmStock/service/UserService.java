package com.PharmStock.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PharmStock.entity.User;
import com.PharmStock.repository.DrugRepository;
import com.PharmStock.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    public DrugRepository drugRepository;

    @Autowired
    public UserRepository userRepository;

    //register
    //username != null, password >= 4 char, username !exist, 
    public Optional<User> createUser(User user){
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            return Optional.empty();
        }
        if(user.getPassword() == null || user.getPassword().length() < 4){
            return Optional.empty();
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return Optional.empty();
        }
        User savedNewUser = userRepository.save(user);
        return Optional.of(savedNewUser);
    }


    //Find User by username
    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }


    //login
    //username and password match a real account existing in db
    public Optional<User> login(String username, String password){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)){
            return optionalUser;
        }
        
        return Optional.empty();
    }
    
}
