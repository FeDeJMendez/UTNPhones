package com.utn.UTNPhones.service;

import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.User;
import com.utn.UTNPhones.domain.enums.Rol;
import com.utn.UTNPhones.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addUser(Client newClient) {
        User user = new User(newClient.getLastname(), newClient.getDni().toString(), Rol.CLIENT, newClient.getId());
        userRepository.save(user);
    }
}
