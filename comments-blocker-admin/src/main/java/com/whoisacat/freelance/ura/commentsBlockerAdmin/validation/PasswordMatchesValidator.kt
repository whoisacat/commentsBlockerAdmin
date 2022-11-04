package com.whoisacat.freelance.ura.commentsBlockerAdmin.validation

import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.UserRegistrationDTO
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordMatchesValidator : ConstraintValidator<PasswordMatches?, Any> {
    override fun initialize(constraintAnnotation: PasswordMatches?) {}
    override fun isValid(obj: Any, context: ConstraintValidatorContext): Boolean {
        val user = obj as UserRegistrationDTO
        return user.password == user.matchingPassword
    }
}