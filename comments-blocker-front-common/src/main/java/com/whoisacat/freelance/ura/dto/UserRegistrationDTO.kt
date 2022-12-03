package com.whoisacat.freelance.ura.dto

import com.whoisacat.freelance.ura.dto.validation.PasswordMatches
import com.whoisacat.freelance.ura.dto.validation.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@PasswordMatches
class UserRegistrationDTO {
    var firstName: @NotNull @NotEmpty String? = null
    var lastName: @NotNull @NotEmpty String? = null
    var password: @NotNull @NotEmpty String? = null
    var matchingPassword: String? = null

    @ValidEmail
    var email: @NotNull @NotEmpty String = ""
}