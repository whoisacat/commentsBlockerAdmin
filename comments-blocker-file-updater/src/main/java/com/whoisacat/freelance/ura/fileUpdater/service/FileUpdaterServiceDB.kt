package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import com.whoisacat.freelance.ura.kafka.dto.Action
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import kotlin.text.StringBuilder

@Service("fileUpdaterService")
@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "db")
class FileUpdaterServiceDB(
    val blockActionService: IpBlockActionService,
    val ioService: IOService,
    @Value("\${com.whoisacat.commentsBlocker.service.db.pageSize}") private val pageSize: Int) {

    @Scheduled(fixedRateString = "\${com.whoisacat.commentsBlocker.service.fileUpdatePeriod}",
        initialDelay = 0, timeUnit = TimeUnit.MINUTES)
    fun removeIpsFromFile() {
        var page = blockActionService.getNotSynchronizedPage(PageRequest.of(0, pageSize),
            Action.REMOVE)
        if (page.content.isEmpty()) return
        var content = ioService.readFile()
        val list = mutableListOf<IpBlockAction>()
        content = replaceFromContent(page.content, content, list)
        for (i in 1 until page.totalPages) {
            val pageable = PageRequest.of(i, pageSize)
            page = blockActionService.getNotSynchronizedPage(pageable, Action.REMOVE)
            content = replaceFromContent(page.content, content, list)
            list.addAll(page.content)
        }
        ioService.replaceContent(content)
        list.forEach{
            it.isSynchronized = true
            blockActionService.save(it)
        }
    }

    private fun replaceFromContent(ips: List<IpBlockAction>, content: String,
        list: MutableList<IpBlockAction>): String {
        var res = content
        getListOfNotSynchronizedStrings(ips).forEach {
            res = res.replace("$it\n", "")
        }
        list.addAll(ips)
        return res.trim()
    }

    @Scheduled(fixedRateString = "\${com.whoisacat.commentsBlocker.service.fileUpdatePeriod}",
        initialDelay = 2, timeUnit = TimeUnit.MINUTES)
    fun addIpsToFile() {
        var page = blockActionService.getNotSynchronizedPage(PageRequest.of(0, pageSize),
            Action.ADD)
        if (page.content.isEmpty()) return
        val sb = StringBuilder(ioService.readFile())
            .append(getNotSynchronizedAsOneString(page.content))
        val list = mutableListOf<IpBlockAction>()
        list.addAll(page.content)
        for (i in 1 until page.totalPages) {
            val pageable = PageRequest.of(i, pageSize)
            page = blockActionService.getNotSynchronizedPage(pageable, Action.ADD)
            sb.append(getNotSynchronizedAsOneString(page.content))
            list.addAll(page.content)
        }
        ioService.appendLine(sb.toString())
        list.stream().peek { it.isSynchronized = true }.forEach(blockActionService::save)
    }

    private fun getNotSynchronizedAsOneString(listIps: List<IpBlockAction>) :String {
        return listIps.stream()
            .map { it.record.ip }
            .peek(String::trim)
            .collect(Collectors.joining("\n","","\n"))
    }

    private fun getListOfNotSynchronizedStrings(listIps: List<IpBlockAction>) :List<String> {
        return listIps.stream()
            .map { it.record.ip }
            .peek(String::trim)
            .collect(Collectors.toList())
    }
}
