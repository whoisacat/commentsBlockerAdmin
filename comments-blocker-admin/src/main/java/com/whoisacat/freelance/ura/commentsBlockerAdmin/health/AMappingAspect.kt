package com.whoisacat.freelance.ura.commentsBlockerAdmin.health

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.collections.ArrayList

@Component
@Aspect
abstract class AMappingAspect(meterRegistry: MeterRegistry, val userService: UserService)
    : AbstractAspect(meterRegistry) {

    protected companion object {
        const val UNDEFINED = "UNDEFINED"
        const val ENDPOINT = "ENDPOINT"
    }
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    fun aroundGet(joinPoint: ProceedingJoinPoint): Any {
        return super.around(joinPoint)
    }

    override fun getTags(joinPoint: ProceedingJoinPoint, ex: Exception?): List<Tag> {
        val tags = ArrayList<Tag>()
        tags.add(Tag.of("USER", userService.getUsernameFromSecurityContext()))
        var endpoint : String
        try {
            endpoint = getEnpointUrl(joinPoint)
        } catch (e: Exception) {
            logger.error("Fail to define endpoint to metric", e)
            endpoint = UNDEFINED
        }
        tags.add(Tag.of(ENDPOINT, endpoint))
        if (ex != null) {
            tags.add(Tag.of(TimedAspect.EXCEPTION_TAG, ex::class.java.name))
        }
        return tags
    }

    protected abstract fun getEnpointUrl(joinPoint: ProceedingJoinPoint): String
}