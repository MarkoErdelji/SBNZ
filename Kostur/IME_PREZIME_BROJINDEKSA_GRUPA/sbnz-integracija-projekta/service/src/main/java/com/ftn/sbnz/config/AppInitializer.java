package com.ftn.sbnz.config;

import com.ftn.sbnz.model.User;
import com.ftn.sbnz.repository.UserRepository;
import org.drools.core.io.impl.ClassPathResource;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;

import java.io.*;
import java.util.List;

@Configuration
public class AppInitializer {

    @Autowired
    KieSession kieSession;

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            List<User> users = userRepository.findAll();
            users.forEach(user -> {
                kieSession.insert(user);
            });

        };
    }
}
