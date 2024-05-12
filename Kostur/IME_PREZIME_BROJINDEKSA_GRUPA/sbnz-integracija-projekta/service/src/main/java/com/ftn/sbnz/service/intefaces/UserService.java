package com.ftn.sbnz.service.intefaces;

import com.ftn.sbnz.model.User;
import com.ftn.sbnz.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User saveUser(User user);
    User getUserByUsername(String username);

    void deleteUserById(Long id);
}
