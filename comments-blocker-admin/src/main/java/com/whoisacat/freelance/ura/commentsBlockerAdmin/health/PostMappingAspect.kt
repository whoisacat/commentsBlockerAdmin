package com.whoisacat.freelance.ura.commentsBlockerAdmin.health

import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.UserService
import io.micrometer.core.instrument.MeterRegistry
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Collectors

@Component
@Aspect
class PostMappingAspect(meterRegistry: MeterRegistry, userService: UserService)
    : AMappingAspect(meterRegistry, userService) {

    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    fun aroundPost(joinPoint: ProceedingJoinPoint): Any {
        return super.around(joinPoint)
    }

    private fun getEndpoint(path: Array<String>): String {
        return Arrays.stream(path).collect(Collectors.joining(","))
    }

    override fun getEnpointUrl(joinPoint: ProceedingJoinPoint): String {
        val method: Method = (joinPoint.signature as MethodSignature).method
        val mapping = method.getAnnotation(GetMapping::class.java)
        return getEndpoint(mapping.path ?: arrayOf<String>())
    }
}
