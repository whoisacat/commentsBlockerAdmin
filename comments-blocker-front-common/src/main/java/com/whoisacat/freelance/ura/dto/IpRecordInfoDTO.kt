package com.whoisacat.freelance.ura.dto

import com.whoisacat.freelance.ura.domain.BlockPeriod

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
