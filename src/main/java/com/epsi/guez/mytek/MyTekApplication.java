package com.epsi.guez.mytek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyTekApplication {

    private static final Logger log = LoggerFactory.getLogger(MyTekApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MyTekApplication.class, args);
    }

}
