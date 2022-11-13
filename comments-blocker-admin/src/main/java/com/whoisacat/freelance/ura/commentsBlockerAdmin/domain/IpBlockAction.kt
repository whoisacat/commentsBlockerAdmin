package com.whoisacat.freelance.ura.commentsBlockerAdmin.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "ip_block_actions")
class IpBlockAction(id: Long? = null,
    startTime: LocalDateTime = LocalDateTime.now(),
    endTime: LocalDateTime? = null,
    isActive: Boolean = true,
    blockPeriod: BlockPeriod = BlockPeriod.FOREVER,
    user: User,
    userExclude: User? = null,
    record: IpRecord,
    @Column(name = "is_synchronised", nullable = false) var isSynchronized: Boolean = false) {

    @Id
    @SequenceGenerator(name = "ip_block_actions_seq",
        sequenceName = "ip_block_actions_seq",
        allocationSize = 1)
    @GeneratedValue(generator = "ip_block_actions_seq")
    @Column(name = "id")
    var id: Long? = id
        private set

    @Column(name = "start_time", updatable = false, nullable = false)
    var startTime: LocalDateTime = startTime
        private set

    @Column(name = "end_time")
    var endTime: LocalDateTime? = endTime
        set(value) {
            field = value
            isActive = value == null
        }

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = isActive

    @Enumerated(EnumType.STRING)
    @Column(name = "block_period", nullable = false)
    var blockPeriod: BlockPeriod = blockPeriod
        private set

    @ManyToOne(fetch = FetchType.EAGER)
    var user: User = user
        private set

    @ManyToOne(fetch = FetchType.EAGER)
    var userExclude: User? = userExclude

    @ManyToOne
    var record: IpRecord = record
        private set
}
