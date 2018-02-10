package com.asierg.multimodule.web.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnviorenmentSysOut {

    private static final Logger logger = LoggerFactory.getLogger(EnviorenmentSysOut.class);

    public EnviorenmentSysOut() {
    }

    @Autowired
    public void printEnvironmentLog(@Value("${spring.profiles.active}") String activeProfile, @Value("${asierg.profile.message}") String msg) {
        logger.info("");
        logger.info("##################################");
        logger.info("##################################");
        logger.info("##              {}             ##", activeProfile.toUpperCase());
        logger.info(msg);
        logger.info("##################################");
        logger.info("##################################");
        logger.info("");
    }
}

