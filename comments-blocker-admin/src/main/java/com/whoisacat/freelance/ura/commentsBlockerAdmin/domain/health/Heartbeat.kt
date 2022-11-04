package com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.health

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "heartbeat")
class Heartbeat(@Column(name = "heartbeat", nullable = false) var heartbeatTime: LocalDateTime) {
    @Id
    @SequenceGenerator(name = "heartbeat", sequenceName = "heartbeat_seq", allocationSize = 1)
    @GeneratedValue(generator = "heartbeat_seq")
    @Column(name = "id")
    var id: Long? = null

}