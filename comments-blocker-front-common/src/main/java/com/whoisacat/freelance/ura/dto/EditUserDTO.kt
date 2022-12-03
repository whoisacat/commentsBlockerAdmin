package com.whoisacat.freelance.ura.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class EditUserDTO(
    val firstName: @NotEmpty @NotNull String?,
    val lastName: @NotEmpty @NotNull String?,
    val email: String,
    val readonly: String
)