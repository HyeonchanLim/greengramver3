package com.green.greengram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GreenGramVer2Application {

    public static void main(String[] args) {
        SpringApplication.run(GreenGramVer2Application.class, args);
    }

}
