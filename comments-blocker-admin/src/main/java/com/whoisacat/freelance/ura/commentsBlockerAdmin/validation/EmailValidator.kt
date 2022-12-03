package com.whoisacat.freelance.ura.commentsBlockerAdmin.validation

import com.whoisacat.freelance.ura.dto.validation.ValidEmail
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailValidator : ConstraintValidator<ValidEmail?, String> {

    override fun initialize(constraintAnnotation: ValidEmail?) {}

    override fun isValid(email: String, context: ConstraintValidatorContext): Boolean {
        return validateEmail(email)
    }

    private fun validateEmail(email: String): Boolean {
        if (email.length < 6) {
            return false
        }
        val pattern =
            Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    companion object {
        private const val EMAIL_PATTERN = ("^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    }
}