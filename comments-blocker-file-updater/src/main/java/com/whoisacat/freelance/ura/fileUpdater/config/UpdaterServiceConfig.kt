package com.whoisacat.freelance.ura.fileUpdater.config

import com.whoisacat.freelance.ura.fileUpdater.service.FileUpdaterService
import com.whoisacat.freelance.ura.fileUpdater.service.FileUpdaterServiceDB
import com.whoisacat.freelance.ura.fileUpdater.service.IOService
import com.whoisacat.freelance.ura.fileUpdater.service.IpBlockActionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "db")
class UpdaterServiceConfig {

    @ConditionalOnProperty(
        value = ["com.whoisacat.commentsBlocker.service.use"],
        havingValue = "db",
        matchIfMissing = true
    )
    @Bean("fileUpdaterService")
    fun fileUpdaterService(blockActionService: IpBlockActionService, ioService: IOService,
                           @Value("\${com.whoisacat.commentsBlocker.service.use}") use: String?): FileUpdaterService? {
        return when (use) {
            "db" -> FileUpdaterServiceDB(blockActionService, ioService)
            else -> throw RuntimeException("determine com.whoisacat.commentsBlocker.service.use (kafka/db) in application.yml")
        }
    }
}