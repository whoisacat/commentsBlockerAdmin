package com.whoisacat.freelance.ura.commentsBlockerAdmin.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Profile("!generate")
@Configuration
class FlywayConfig(private val dataSource: DataSource) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(dataSource)
            .load()
    }
}