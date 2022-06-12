package com.utn.UTNPhones.service;

import com.utn.UTNPhones.domain.Backoffice;
import com.utn.UTNPhones.domain.Client;
import com.utn.UTNPhones.domain.Person;
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


    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void addUser(Person newPerson) {
        if (newPerson.getClass() == Client.class) {
            User user = new User(newPerson.getLastname(), newPerson.getDni().toString(), Rol.ROLE_CLIENT, newPerson.getId());
            userRepository.save(user);
        } else if (newPerson.getClass() == Backoffice.class) {
            User user = new User(newPerson.getLastname(), newPerson.getDni().toString(), Rol.ROLE_BACKOFFICE, newPerson.getId());
            userRepository.save(user);
        }
    }
}
