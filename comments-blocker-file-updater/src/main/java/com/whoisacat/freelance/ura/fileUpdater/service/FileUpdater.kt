package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class FileUpdater(private val blockActionService: IpBlockActionService,
    private val ipRecordService: IpRecordService) {


    @Scheduled(fixedRateString = "\${com.whoisacat.commentsBlocker.service.fileUpdatePeriod}",
        initialDelay = 10, timeUnit = TimeUnit.MINUTES)
    fun addIpsToFile() {
        val pageSize = 100
        var page = blockActionService.getActivePage(Pageable.ofSize(pageSize))
        writeUnwrited(page.content)
        while (page.hasNext()) {
            var i = 0
            val pageable = PageRequest.of(++i, pageSize)
            page = blockActionService.getActivePage(pageable)
            writeUnwrited(page.content)
        }
    }

    private fun writeUnwrited(listIps: List<IpBlockAction>) {
        TODO("Not yet implemented")
    }
}
