package com.whoisacat.freelance.ura.commentsBlockerAdmin.health

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.health.Heartbeat
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.health.HeartbeatRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class HeartbeatHealthIndicator(private val repository: HeartbeatRepository) : HealthIndicator {

    @Value("\${com.whoisacat.commentsBlocker.health.heartbeat.period}")
    private val HEARTBEAT_PERIOD = 1000
    override fun health(): Health {
        val now = LocalDateTime.now()
        val heartbeats: List<Heartbeat> = try {
            repository.getAllByHeartbeatTimeIsAfter(now.minusSeconds((HEARTBEAT_PERIOD / 1000 * 5).toLong()))
        } catch (e: Exception) {
            return Health.down().withDetail("cause", repository.javaClass.simpleName + " unavailable").build()
        }
        return if (heartbeats.size < HEARTBEAT_PERIOD / 1000 * 4) {
            if (heartbeats.stream()
                    .anyMatch { h: Heartbeat -> h.heartbeatTime.isAfter(now.minusSeconds((HEARTBEAT_PERIOD / 1000 * 2).toLong())) }
            ) {
                Health.status("RISE").withDetail("count_of_heartbeats", heartbeats.size).build()
            } else {
                Health.down().build()
            }
        } else {
            Health.up().build()
        }
    }

    @Scheduled(fixedRateString = "\${com.whoisacat.commentsBlocker.health.heartbeat.period}", initialDelay = 1000)
    fun heartbeat() {
        repository.save(Heartbeat(LocalDateTime.now()))
    }

    @Scheduled(cron = "0 0 0 1 * *")
    fun clearHeartbeat() {
        repository.deleteAllByHeartbeatTimeIsBefore(LocalDateTime.now().minusMonths(1))
    }
}