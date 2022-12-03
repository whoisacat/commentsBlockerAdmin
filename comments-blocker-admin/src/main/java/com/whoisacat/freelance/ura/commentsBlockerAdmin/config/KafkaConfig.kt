package com.whoisacat.freelance.ura.commentsBlockerAdmin.config

import com.whoisacat.freelance.ura.kafka.dto.IpActionMessage
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfig {

    @Value("\${com.whoisacat.commentsBlocker.service.kafkaServer}")
    lateinit var SERVER: String
    private val logger: Logger = LoggerFactory.getLogger(KafkaConfig::class.java)

    private fun producerFactoryMessage(): ProducerFactory<String, IpActionMessage> {
        val configProps: MutableMap<String, Any> = HashMap()
        logger.info("producerFactoryMessage server is $SERVER")
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = SERVER
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplateMessage(@Value("\${com.whoisacat.commentsBlocker.service.use}") use: String?): KafkaTemplate<String, IpActionMessage>? {
        if (!(use != null && use == "kafka")) return null
        return KafkaTemplate(producerFactoryMessage())
    }

    @Bean
    fun kafkaAdmin(@Value("\${com.whoisacat.commentsBlocker.service.use}") use: String?): KafkaAdmin? {
        if (!(use != null && use == "kafka")) return null
        logger.info("kafkaAdmin server is $SERVER")
        val configs: MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = SERVER
        configs[AdminClientConfig.CLIENT_ID_CONFIG] = CLIENT_ID_CONFIG
        return KafkaAdmin(configs)
    }

    @Bean
    fun topicDelete(): NewTopic {
        return NewTopic(DELETE_IP_TOPIC, 2, 1.toShort())
    }

    @Bean
    fun topicInsert(): NewTopic {
        return NewTopic(INSERT_IP_TOPIC, 2, 1.toShort())
    }

    companion object {
        private const val CLIENT_ID_CONFIG = "admin1"
        const val INSERT_IP_TOPIC = "ip.insert"
        const val DELETE_IP_TOPIC = "ip.delete"
    }
}