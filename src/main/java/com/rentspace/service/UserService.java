package com.rentspace.service;

import com.rentspace.DTO.UserDTO;
import com.rentspace.entity.User;
import com.rentspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User salvaNovoCadastro(UserDTO userDTO){
        User user = new User(userDTO.getName(), userDTO.getProfilePhoto(), userDTO.getEmail(), userDTO.getTelephone(), userDTO.getWebSite());
        return userRepository.save(user);

    }
}
