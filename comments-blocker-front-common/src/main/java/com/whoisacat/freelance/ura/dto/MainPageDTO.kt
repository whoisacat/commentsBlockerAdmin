package com.whoisacat.freelance.ura.dto

import org.springframework.data.domain.Page

class MainPageDTO(public val adminAuth: Boolean, val page: Page<IpRecordInfoDTO>)