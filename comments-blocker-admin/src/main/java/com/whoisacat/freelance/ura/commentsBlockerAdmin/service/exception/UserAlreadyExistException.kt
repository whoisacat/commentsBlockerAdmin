package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException

class UserAlreadyExistException(email: String) :
    WHOClientRequestException("There is an account with that email address: " + email)