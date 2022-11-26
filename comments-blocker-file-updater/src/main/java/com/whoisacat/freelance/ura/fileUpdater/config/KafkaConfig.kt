package com.whoisacat.freelance.ura.fileUpdater.config

import com.whoisacat.freelance.ura.dto.IpActionMessage
import com.whoisacat.freelance.ura.fileUpdater.service.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
@ConditionalOnProperty(value = ["com.whoisacat.commentsBlocker.service.use"], havingValue = "kafka")
class KafkaConfig {

    @Value("\${com.whoisacat.commentsBlocker.service.kafka.groupId}")
    val groupId: String? = null
    private fun consumerFactoryMessage(): ConsumerFactory<String, IpActionMessage> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = SERVER
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(), JsonDeserializer(
                IpActionMessage::class.java
            )
        )
    }

    @Bean
    fun kafkaListenerContainerFactoryMessage(): ConcurrentKafkaListenerContainerFactory<String, IpActionMessage> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, IpActionMessage>()
        factory.setConsumerFactory(consumerFactoryMessage())
        return factory
    }

    @Autowired lateinit var ioService: IOService

    @Bean("fileUpdaterService")
    fun fileUpdaterService(@Value("\${com.whoisacat.commentsBlocker.service.use}") use: String?)
    : FileUpdaterServiceKafka {
        return when (use) {
            "kafka" -> FileUpdaterServiceKafka(ioService)
            else -> throw RuntimeException("determine com.whoisacat.commentsBlocker.service.use (kafka/db) in application.yml")
        }
    }

    companion object {
        private const val SERVER = "localhost:9091"
        const val INSERT_IP_TOPIC = "ip.insert"
        const val DELETE_IP_TOPIC = "ip.delete"
    }
}