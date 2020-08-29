package com.shun;

import com.thebeastshop.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ForestScan(basePackages = "com.shun.net")
@SpringBootApplication
public class AutoAliyundnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoAliyundnsApplication.class, args);
    }

}
