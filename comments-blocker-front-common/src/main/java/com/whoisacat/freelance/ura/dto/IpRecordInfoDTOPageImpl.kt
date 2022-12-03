package com.whoisacat.freelance.ura.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class IpRecordInfoDTOPageImpl
@JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @JsonProperty("content") val resultList: List<IpRecordInfoDTO>?,
    @JsonProperty("page") val pageNumber: Int,
    @JsonProperty("size") val pageSize: Int,
    @JsonProperty("total") val total: Long,
    @JsonProperty("searchText") val searchText: String = "",
    @JsonProperty("ipToBlock") val ipToBlock: String = "")