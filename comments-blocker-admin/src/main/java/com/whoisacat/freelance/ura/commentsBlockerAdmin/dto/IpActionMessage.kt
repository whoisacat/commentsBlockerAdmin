package com.whoisacat.freelance.ura.commentsBlockerAdmin.dto

import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.Action

data class IpActionMessage(
    var id: Long,
    var ip: String,
    var action: Action)
