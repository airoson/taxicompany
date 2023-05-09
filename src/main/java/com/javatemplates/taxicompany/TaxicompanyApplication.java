package com.javatemplates.taxicompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TaxicompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxicompanyApplication.class, args);
    }

}
