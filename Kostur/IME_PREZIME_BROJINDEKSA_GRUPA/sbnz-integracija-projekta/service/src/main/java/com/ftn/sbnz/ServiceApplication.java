package com.ftn.sbnz;

import java.io.*;
import java.util.Arrays;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.kie.api.conf.EventProcessingOption.STREAM;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ftn.sbnz.repository"})
@EntityScan(basePackages = {"com.ftn.sbnz.*"})
public class ServiceApplication  {

	private static Logger log = LoggerFactory.getLogger(ServiceApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ServiceApplication.class, args);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);

		StringBuilder sb = new StringBuilder("Application beans:\n");
		for (String beanName : beanNames) {
			sb.append(beanName + "\n");
		}
		log.info(sb.toString());
	}


	@Bean
	KieSession kieSession() throws IOException {
    InputStream drtInputStream = new FileInputStream(new File("./kjar/src/main/resources/rules/cep/base.drt"));
    InputStream excelInputStream = new FileInputStream(new File("./kjar/src/main/resources/rules/cep/base.xlsx"));

    InputStream drtKillsInputStream = new FileInputStream(new File("./kjar/src/main/resources/rules/cep/base_kills.drt"));
    InputStream excelKillsInputStream = new FileInputStream(new File("./kjar/src/main/resources/rules/cep/base_kills.xlsx"));
    ExternalSpreadsheetCompiler compiler = new ExternalSpreadsheetCompiler();
    String drlRules = compiler.compile(excelInputStream,drtInputStream, 2, 1);
    String drlKillsRules = compiler.compile(excelKillsInputStream,drtKillsInputStream, 2, 1);
    String cepDrl = readFileToString("./kjar/src/main/resources/rules/cep/cep.drl");
    String forwardAimbotDrl = readFileToString("./kjar/src/main/resources/rules/cep/forward_aimbot.drl");
    String forwardWallhackDrl = readFileToString("./kjar/src/main/resources/rules/cep/forward_wallhack.drl");
    String backwardsDrl = readFileToString("./kjar/src/main/resources/rules/cep/backwards.drl");
    String querryDrl = readFileToString("./kjar/src/main/resources/rules/cep/querry.drl");
    KieHelper kieHelper = new KieHelper();
    kieHelper.addContent(drlRules, ResourceType.DRL);
    kieHelper.addContent(drlKillsRules, ResourceType.DRL);
    kieHelper.addContent(cepDrl, ResourceType.DRL);
    kieHelper.addContent(forwardAimbotDrl, ResourceType.DRL);
    kieHelper.addContent(forwardWallhackDrl, ResourceType.DRL);
    kieHelper.addContent(backwardsDrl, ResourceType.DRL);
    kieHelper.addContent(querryDrl, ResourceType.DRL);

    KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
    config.setOption(EventProcessingOption.STREAM);

    return kieHelper.build(config).newKieSession();
}

	 private static String readFileToString(String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(new File(filePath));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		return kContainer;
	}

	/*
	 * KieServices ks = KieServices.Factory.get(); KieContainer kContainer =
	 * ks.newKieContainer(ks.newReleaseId("drools-spring-v2",
	 * "drools-spring-v2-kjar", "0.0.1-SNAPSHOT")); KieScanner kScanner =
	 * ks.newKieScanner(kContainer); kScanner.start(10_000); KieSession kSession =
	 * kContainer.newKieSession();
	 */

}

