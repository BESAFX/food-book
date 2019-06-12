package com.besafx.app.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {

    private final static Logger LOG = LoggerFactory.getLogger(Initializer.class);


    @Override
    public void run(String... args) {

        startup();

    }

    private void startup() {

    }
}
