package com.joy.xxfy.informationaldxn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class InformationalDxnApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationalDxnApplication.class, args);
    }

}
