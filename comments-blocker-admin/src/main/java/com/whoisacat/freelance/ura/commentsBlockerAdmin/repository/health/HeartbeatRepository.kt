package com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.health

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.health.Heartbeat
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

interface HeartbeatRepository : CrudRepository<com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.health.Heartbeat?, Long?> {
    fun getAllByHeartbeatTimeIsAfter(time: LocalDateTime?): List<com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.health.Heartbeat>
    fun deleteAllByHeartbeatTimeIsBefore(time: LocalDateTime?)
}