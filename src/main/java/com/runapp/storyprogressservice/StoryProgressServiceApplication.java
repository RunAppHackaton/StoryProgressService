package com.runapp.storyprogressservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class StoryProgressServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryProgressServiceApplication.class, args);
    }

}
