package com.whoisacat.freelance.ura.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class IpActionMessage(
    @JsonProperty("ip") var ip: String,
    @JsonProperty("action") var action: Action
)
