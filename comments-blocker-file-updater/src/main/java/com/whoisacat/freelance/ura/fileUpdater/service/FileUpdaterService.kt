package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.fileUpdater.dto.IpActionMessage
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener

interface FileUpdaterService {

    fun removeIpsFromFile()

    fun addIpsToFile()

    fun removeIpsFromFile(record: ConsumerRecord<String, IpActionMessage>)
}
