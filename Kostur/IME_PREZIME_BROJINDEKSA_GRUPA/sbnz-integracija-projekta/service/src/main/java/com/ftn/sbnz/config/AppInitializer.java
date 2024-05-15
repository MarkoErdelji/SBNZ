package com.ftn.sbnz.config;


import com.ftn.sbnz.model.User;
import com.ftn.sbnz.repository.UserRepository;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppInitializer {
    @Autowired
    KieSession kieSession;
    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            // This code will run after the application context is loaded
            List<User> users = userRepository.findAll();
            users.forEach(user -> {
                kieSession.insert(user);
            });
        };
    }
}
