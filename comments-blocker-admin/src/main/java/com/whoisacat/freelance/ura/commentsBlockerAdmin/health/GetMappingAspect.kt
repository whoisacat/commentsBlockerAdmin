package com.whoisacat.freelance.ura.commentsBlockerAdmin.health

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import io.micrometer.core.aop.TimedAspect.EXCEPTION_TAG
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Component
@Aspect
class GetMappingAspect(meterRegistry: MeterRegistry, userService: UserService)
    : AMappingAspect(meterRegistry, userService) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    @Around("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    override fun aroundGet(joinPoint: ProceedingJoinPoint): Any {
        return super.around(joinPoint)
    }

    private fun getEndpoint(path: Array<String>): String {
        return Arrays.stream(path).collect(Collectors.joining(","))
    }

    override fun getTags(joinPoint: ProceedingJoinPoint, ex: Exception?): List<Tag> {
        val tags = ArrayList<Tag>()
        tags.add(Tag.of("USER", userService.getUsernameFromSecurityContext()))
        var endpoint : String
        try {
            val method: Method = (joinPoint.signature as MethodSignature).method
            val mapping = method.getAnnotation(GetMapping::class.java)
            endpoint = getEndpoint(mapping.path ?: arrayOf<String>())
        } catch (e: Exception) {
            logger.error("Fail to define endpoint to metric", e)
            endpoint = UNDEFINED
        }
        tags.add(Tag.of(ENDPOINT, endpoint))
        if (ex != null) {
            tags.add(Tag.of(EXCEPTION_TAG, ex::class.java.name))
        }
        return tags
    }

    override fun getEnpointUrl(joinPoint: ProceedingJoinPoint): String {
        val method: Method = (joinPoint.signature as MethodSignature).method
        val mapping = method.getAnnotation(GetMapping::class.java)
        return getEndpoint(mapping.path ?: arrayOf<String>())
    }
}
