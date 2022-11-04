package com.whoisacat.freelance.ura.commentsBlockerAdmin.dto

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.BlockPeriod

data class IpRecordInfoDTO(val blockActionId: Long,
    val ip: String,
    val startTime: String,
    val endTime: String? = null,
    val country: String = "",
    val city: String = "",
    val blockPeriod: BlockPeriod = BlockPeriod.FOREVER,
    val user: String?,
    val userExclude: String? = null) {
}
