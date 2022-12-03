package com.whoisacat.freelance.ura.dto

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

open class WHOPageImpl<T : Any?>(resultList: List<T>?,
    pageable: PageRequest?,
    total: Long,
    val searchText: String?,
    val ipToBlock: String = "") : PageImpl<T>(
    resultList?: emptyList(), pageable!!, total)