package com.whoisacat.freelance.ura.commentsBlockerAdmin.health

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer
import org.aspectj.lang.ProceedingJoinPoint
import java.time.Duration

abstract class AbstractAspect(private val meterRegistry: MeterRegistry) {
    protected open fun around(joinPoint: ProceedingJoinPoint): Any {
        val sample = Timer.start(meterRegistry)
        val result : Any
        var ex : Exception? = null
        try {
            result = joinPoint.proceed()
        } catch (e : Exception) {
            ex = e
            throw e
        } finally {
            try {
                sample.stop(
                    Timer.builder(this.javaClass.name)
                        .publishPercentiles(0.5.toDouble(), 0.95.toDouble())
                        .publishPercentileHistogram()
                        .sla(Duration.ofMillis(100L))
                        .minimumExpectedValue(Duration.ofMillis(1L))
                        .maximumExpectedValue(Duration.ofSeconds(30L))
                        .tags(this.getTags(joinPoint, ex))
                        .register(meterRegistry)
                )
            } catch (next: Exception) {
            }
        }
        return result
    }

    abstract fun getTags(joinPoint: ProceedingJoinPoint, ex: Exception?): List<Tag>
}
