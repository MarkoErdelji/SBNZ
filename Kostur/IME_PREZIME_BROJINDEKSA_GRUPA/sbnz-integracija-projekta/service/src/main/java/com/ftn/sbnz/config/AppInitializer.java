package com.ftn.sbnz.config;

import com.ftn.sbnz.model.Tournament;
import com.ftn.sbnz.model.User;
import com.ftn.sbnz.repository.UserRepository;
import org.drools.core.io.impl.ClassPathResource;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
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
        kieSession.insert(new Tournament("Major","Copenhagen 2022"));
        kieSession.insert(new Tournament("Major","Copenhagen 2021"));
        kieSession.insert(new Tournament("Major","Paris 2022"));
        kieSession.insert(new Tournament("Major","Paris 2021"));
        kieSession.insert(new Tournament("Copenhagen 2022","G2"));
        kieSession.insert(new Tournament("Copenhagen 2021","Astralis"));
        kieSession.insert(new Tournament("Paris 2022","Na Vi"));
        kieSession.insert(new Tournament("Paris 2021","FNATIC"));
        kieSession.insert(new Tournament("G2","m0NESY"));
        kieSession.insert(new Tournament("G2","NiKo"));
        kieSession.insert(new Tournament("G2","Hunter"));
        kieSession.insert(new Tournament("G2","HooXi"));
        kieSession.insert(new Tournament("G2","nexa"));
        kieSession.insert(new Tournament("Astralis","dev1ce"));
        kieSession.insert(new Tournament("Astralis","Gla1ve"));
        kieSession.insert(new Tournament("Astralis","Xyp9x"));
        kieSession.insert(new Tournament("Astralis","dupreeh"));
        kieSession.insert(new Tournament("Astralis","Magisk"));
        kieSession.insert(new Tournament("Na Vi","jL"));
        kieSession.insert(new Tournament("Na Vi","b1t"));
        kieSession.insert(new Tournament("Na Vi","Aleksib"));
        kieSession.insert(new Tournament("Na Vi","iM"));
        kieSession.insert(new Tournament("Na Vi","w0nderful"));
        kieSession.insert(new Tournament("FNATIC","Krimz"));
        kieSession.insert(new Tournament("FNATIC","afro"));
        kieSession.insert(new Tournament("FNATIC","MATYS"));
        kieSession.insert(new Tournament("FNATIC","bodyy"));
        kieSession.insert(new Tournament("FNATIC","BlameF"));

        kieSession.insert("1");
        kieSession.fireAllRules();
        return args -> {
            List<User> users = userRepository.findAll();
            users.forEach(user -> {
                kieSession.insert(user);
            });

        };
    }
}
