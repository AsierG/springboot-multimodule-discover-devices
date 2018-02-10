package com.asierg.multimodule.commandline;

import com.asierg.multimodule.service.services.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.asierg.multimodule.service"})
@EntityScan(basePackages = {"com.asierg.multimodule.domain"})
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.asierg.multimodule.service", "com.asierg.multimodule.domain", "com.asierg.multimodule.commandline"})
public class CommandLineApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    @Autowired
    ProcessService processService;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(new Object[]{CommandLineApplication.class});
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            processService.launchCmdDiscoverProcess(args[0]);
        } else {
            logger.error("Parameters needed");
        }
        System.exit(0);
    }
}
