package com.whoisacat.freelance.ura.commentsBlockerAdmin.dto

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class WHOPageImpl<T : Any?>(resultList: List<T>?,
    pageable: Pageable?,
    total: Long,
    val searchText: String?,
    val ipToBlock: String = "") : PageImpl<T>(
    resultList!!, pageable!!, total)