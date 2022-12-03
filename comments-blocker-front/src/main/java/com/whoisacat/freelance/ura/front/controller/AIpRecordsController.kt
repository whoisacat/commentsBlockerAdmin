package com.whoisacat.freelance.ura.front.controller

import com.whoisacat.freelance.ura.dto.IpRecordInfoDTO
import com.whoisacat.freelance.ura.dto.IpRecordInfoDTOPageImpl
import com.whoisacat.freelance.ura.dto.WHOPageImpl
import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException
import com.whoisacat.freelance.ura.front.config.AdminApiAttributes
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

open class AIpRecordsController(protected val restTemplate: RestTemplate,
                                protected val adminApiAttributes: AdminApiAttributes
) {

    fun getIpRecordInfoDTOS(page: Int, text: String?, requestEntity: HttpEntity<Void>
                            , resourceUrl: String): Page<IpRecordInfoDTO> {
        val response = restTemplate.exchange(
            "${adminApiAttributes.protocol}://${adminApiAttributes.host}:${adminApiAttributes.port}/api/$resourceUrl?page=$page"
                    + if (!text.isNullOrBlank()) "&search_text=${text}" else "",
            HttpMethod.GET, requestEntity,
            IpRecordInfoDTOPageImpl::class.java
        )
        val pageable = PageRequest.of(
            response.body?.pageNumber ?: 0,
            response.body?.pageSize ?: throw WHOClientRequestException("didntGetAnyRecordOrPageSize")
        )
        val ips: Page<IpRecordInfoDTO> = WHOPageImpl(
            resultList = response.body?.resultList ?: emptyList(),
            pageable = pageable, total = response.body?.total ?: 0, searchText = text
        )
        return ips
    }
}
