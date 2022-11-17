package com.whoisacat.freelance.ura.fileUpdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FileUpdaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileUpdaterApplication.class, args);
    }
}
