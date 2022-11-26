package com.whoisacat.freelance.ura.fileUpdater.service

import com.whoisacat.freelance.ura.dto.IpActionMessage
import org.apache.kafka.clients.consumer.ConsumerRecord

interface FileUpdaterService {

    fun removeIpsFromFile()

    fun addIpsToFile()

    fun removeIpsFromFile(record: ConsumerRecord<String, IpActionMessage>)
}
