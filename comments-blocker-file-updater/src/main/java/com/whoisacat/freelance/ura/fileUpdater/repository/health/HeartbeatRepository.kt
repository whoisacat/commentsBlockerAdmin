package com.whoisacat.freelance.ura.fileUpdater.repository.health

import com.whoisacat.freelance.ura.fileUpdater.domain.health.Heartbeat
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime

interface HeartbeatRepository : CrudRepository<Heartbeat?, Long?> {
    fun getAllByHeartbeatTimeIsAfter(time: LocalDateTime?): List<Heartbeat>
    fun deleteAllByHeartbeatTimeIsBefore(time: LocalDateTime?)
}