package com.whoisacat.freelance.ura.dto.validation

import java.lang.annotation.Documented
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
@Documented
annotation class ValidEmail(
    val message: String = "Invalid email",
    val groups: Array<KClass<*>> = [],
//    val payload: Array<KClass<out Payload?>> = []
    val payload: Array<KClass<out Any>> = []
)