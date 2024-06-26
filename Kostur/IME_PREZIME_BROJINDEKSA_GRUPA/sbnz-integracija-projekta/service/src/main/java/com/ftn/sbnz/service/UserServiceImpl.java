package com.ftn.sbnz.service;

import com.ftn.sbnz.dto.UserAchievementsDTO;
import com.ftn.sbnz.exception.NotFoundException;
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
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User with the username already exists.");
        }

        User savedUser = userRepository.save(user);

        kieSession.insert(savedUser);
        kieSession.fireAllRules();

        return savedUser;
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User getActiveUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        User currentUser = null;

        QueryResults queryResults = kieSession.getQueryResults("getUserById",user.getId());
        for (QueryResultsRow row : queryResults) {
            currentUser = (User) row.get("$user");
        }
        if (currentUser != null && !currentUser.isSuspended()) {
            return currentUser;
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

      public List<UserAchievementsDTO> getUserAchievements(Long userId) {
        QueryResults queryResults = kieSession.getQueryResults("getUserById", userId);
        if (queryResults.size() == 0) {
            throw new NotFoundException("User not found with id: " + userId);
        }

        User user = null;
        for (QueryResultsRow row : queryResults) {
            user = (User) row.get("$user");
        }

        if (user == null) {
            throw new NotFoundException("User not found with id: " + userId);
        }

        return user.getAchievements().stream()
            .map(achievement -> {
                UserAchievementsDTO achievementDTO = new UserAchievementsDTO();
                achievementDTO.setName(achievement.getName());
                return achievementDTO;
            })
            .collect(Collectors.toList());
    }
}
