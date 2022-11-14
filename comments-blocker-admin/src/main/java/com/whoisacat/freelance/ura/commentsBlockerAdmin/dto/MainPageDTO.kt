package com.whoisacat.freelance.ura.commentsBlockerAdmin.dto

import org.springframework.data.domain.Page

class MainPageDTO(public val adminAuth: Boolean, val page: Page<IpRecordInfoDTO>)