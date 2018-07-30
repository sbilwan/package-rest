package com.exercise.rest;

import com.exercise.rest.apiConsumer.ForexApiConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PackageRestApplication {


    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(PackageRestApplication.class, args);
        ForexApiConsumer forexApiConsumer = context.getBean(ForexApiConsumer.class);
        forexApiConsumer.loadSupportedCurrencies();
    }
}
