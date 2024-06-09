package com.ftn.sbnz.service;

import com.ftn.sbnz.model.User;
import com.ftn.sbnz.model.UserInfo;
import com.ftn.sbnz.repository.UserRepository;
import com.ftn.sbnz.service.intefaces.UserService;
import org.drools.core.ClassObjectFilter;
import org.drools.core.impl.StatefulKnowledgeSessionImpl;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
     KieSession kieSession;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();

        QueryResults queryResults = kieSession.getQueryResults("getAllUsersNotAdmin");
        for (QueryResultsRow row : queryResults) {
            User user = (User) row.get("$user");
            users.add(user);
        }

        return users;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User getActiveUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null && !user.isSuspended()) {
            return user;
        } else {
            throw new IllegalArgumentException("User does not exist or is suspended!");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {throw new UsernameNotFoundException(username);}

        return new UserInfo(user);
    }
}
