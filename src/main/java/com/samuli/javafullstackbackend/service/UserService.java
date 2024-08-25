package com.samuli.javafullstackbackend.service;

import com.samuli.javafullstackbackend.exception.UserAlreadyExistsException;
import com.samuli.javafullstackbackend.exception.UserNotFoundException;
import com.samuli.javafullstackbackend.model.User;
import com.samuli.javafullstackbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User newUser){

        if(userRepository.existsByEmail(newUser.getEmail())){
            throw new UserAlreadyExistsException();
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        return userRepository.save(newUser);

    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        logger.debug("asdasd");
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    public User updateUser(User updatedUser, Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setEmail(updatedUser.getEmail());
                    user.setName(updatedUser.getName());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    public String deleteUser(Long id){
        return "User " + id + " deleted.";
    }
}
