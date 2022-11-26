package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.dto.IpActionMessage
import com.whoisacat.freelance.ura.fileUpdater.config.KafkaConfig.Companion.DELETE_IP_TOPIC
import com.whoisacat.freelance.ura.fileUpdater.config.KafkaConfig.Companion.INSERT_IP_TOPIC
import com.whoisacat.freelance.ura.fileUpdater.domain.IpBlockAction
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import java.lang.RuntimeException
import java.util.stream.Collectors

class FileUpdaterServiceKafka(private val blockActionService: IpBlockActionService,
                              private val ioService: IOService) : FileUpdaterService {

    override fun removeIpsFromFile() {
        throw RuntimeException("operation not permitted")
    }

    @KafkaListener(topics = [DELETE_IP_TOPIC], containerFactory = "kafkaListenerContainerFactoryMessage")
    override fun removeIpsFromFile(record: ConsumerRecord<String, IpActionMessage>) {
        println("record: key ${record.key()}, value ${record.value()}")
//        var page = blockActionService.getNotSynchronizedPage(
//            PageRequest.of(0, pageSize),
//            Action.REMOVE
//        )
//        if (page.content.isEmpty()) return
//        var content = ioService.readFile()
//        val list = mutableListOf<IpBlockAction>()
//        content = replaceFromContent(page.content, content, list)
//        for (i in 1 until page.totalPages) {
//            val pageable = PageRequest.of(i, pageSize)
//            page = blockActionService.getNotSynchronizedPage(pageable, Action.REMOVE)
//            content = replaceFromContent(page.content, content, list)
//            list.addAll(page.content)
//        }
//        ioService.replaceContent(content)
//        list.forEach{
//            it.isSynchronized = true
//            blockActionService.save(it)
//        }
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

    override fun addIpsToFile() {
//        var page = blockActionService.getNotSynchronizedPage(
//            PageRequest.of(0, pageSize),
//            Action.ADD
//        )
//        if (page.content.isEmpty()) return
//        val sb = StringBuilder(ioService.readFile())
//            .append(getNotSynchronizedAsOneString(page.content))
//        val list = mutableListOf<IpBlockAction>()
//        list.addAll(page.content)
//        for (i in 1 until page.totalPages) {
//            val pageable = PageRequest.of(i, pageSize)
//            page = blockActionService.getNotSynchronizedPage(pageable, Action.ADD)
//            sb.append(getNotSynchronizedAsOneString(page.content))
//            list.addAll(page.content)
//        }
//        ioService.appendLine(sb.toString())
//        list.stream().peek { it.isSynchronized = true }.forEach(blockActionService::save)
    }

    private fun getNotSynchronizedAsOneString(listIps: List<IpBlockAction>) :String {
        return listIps.stream()
            .map { it.record.ip }
            .peek(String::trim)
            .collect(Collectors.joining("\n", "", "\n"))
    }

    private fun getListOfNotSynchronizedStrings(listIps: List<IpBlockAction>) :List<String> {
        return listIps.stream()
            .map { it.record.ip }
            .peek(String::trim)
            .collect(Collectors.toList())
    }
}