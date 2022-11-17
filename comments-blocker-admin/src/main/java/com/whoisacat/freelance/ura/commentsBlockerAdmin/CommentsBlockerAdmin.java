package com.whoisacat.freelance.ura.commentsBlockerAdmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CommentsBlockerAdmin {
    public static void main(String[] args) {
        SpringApplication.run(CommentsBlockerAdmin.class, args);
    }
}
