package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.dto.Action
import com.whoisacat.freelance.ura.dto.IpActionMessage
import com.whoisacat.freelance.ura.fileUpdater.config.KafkaConfig.Companion.DELETE_IP_TOPIC
import com.whoisacat.freelance.ura.fileUpdater.config.KafkaConfig.Companion.INSERT_IP_TOPIC
import com.whoisacat.freelance.ura.fileUpdater.service.exception.WHORequestClientException
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

class FileUpdaterServiceKafka(private val ioService: IOService) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = [DELETE_IP_TOPIC], containerFactory = "kafkaListenerContainerFactoryMessage")
    fun removeIpsFromFile(record: ConsumerRecord<String, IpActionMessage>) {
        logger.info("record: key ${record.key()}, value ${record.value()}")
        if (!record.value().action.equals(Action.REMOVE)) throw WHORequestClientException("wrongOperation")
        var content = ioService.readFile()
        content = replaceFromContent(record.value().ip, content)
        ioService.replaceContent(content)
    }

    private fun replaceFromContent(ip: String, content: String): String {
        return content.replace("$ip\n", "").trim() + "\n"
    }

    @KafkaListener(topics = [INSERT_IP_TOPIC], containerFactory = "kafkaListenerContainerFactoryMessage")
    fun addIpsToFile(record: ConsumerRecord<String, IpActionMessage>) {
        logger.info("record: key ${record.key()}, value ${record.value()}")
        if (!record.value().action.equals(Action.ADD)) throw WHORequestClientException("wrongOperation")
        val content = ioService.readFile()
        if (content.contains(record.value().ip, true)) throw WHORequestClientException("ipAlreadyExists")
        ioService.appendLine(StringBuilder(content)
            .append(record.value().ip).append("\n").toString())
    }
}