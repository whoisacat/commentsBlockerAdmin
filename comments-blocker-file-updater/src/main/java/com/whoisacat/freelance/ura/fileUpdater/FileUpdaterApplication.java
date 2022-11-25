package com.whoisacat.freelance.ura.fileUpdater;

import com.whoisacat.freelance.ura.fileUpdater.service.IOService;
import com.whoisacat.freelance.ura.fileUpdater.service.IpBlockActionService;
import com.whoisacat.freelance.ura.fileUpdater.service.FileUpdaterService;
import com.whoisacat.freelance.ura.fileUpdater.service.FileUpdaterServiceDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FileUpdaterApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileUpdaterApplication.class, args);
    }

    @Autowired public IpBlockActionService blockActionService;
    @Autowired public IOService ioService;

    @ConditionalOnProperty(value = {"com.whoisacat.commentsBlocker.service.use"}, havingValue = "db",
            matchIfMissing = true)
    @Bean("fileUpdaterService")
    public FileUpdaterService fileUpdaterService(@Value("${com.whoisacat.commentsBlocker.service.use}") String use) {
        return switch (use) {
            case "db" -> new FileUpdaterServiceDB(blockActionService, ioService);
            default ->
                    throw new RuntimeException("determine com.whoisacat.commentsBlocker.service.use (kafka/db) in application.yml");
        };
    }
}
