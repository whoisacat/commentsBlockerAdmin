package com.whoisacat.freelance.ura.commentsBlockerAdmin.validation

import com.whoisacat.freelance.ura.dto.validation.PasswordMatchesValidator
import java.lang.annotation.Documented
//import java.lang.annotation.Retention
//import java.lang.annotation.RetentionPolicy
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