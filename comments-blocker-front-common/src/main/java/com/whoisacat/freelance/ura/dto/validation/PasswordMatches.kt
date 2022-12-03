package com.whoisacat.freelance.ura.dto.validation

import java.lang.annotation.Documented
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchesValidator::class])
@Documented
annotation class PasswordMatches(val message: String = "Passwords don't match",
    val groups: Array<KClass<*>> = [],
//    val payload: Array<KClass<out Payload?>> = [])
    val payload: Array<KClass<out Any>> = [])