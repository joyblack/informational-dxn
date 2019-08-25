package com.joy.xxfy.informationaldxn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// 开始事务
@EnableTransactionManagement
// 开启定时任务
@EnableScheduling
public class InformationalDxnApplication {
    public static void main(String[] args) {
        SpringApplication.run(InformationalDxnApplication.class, args);
    }
}
