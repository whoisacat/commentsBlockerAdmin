package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

class UserAlreadyExistException(email: String) :
    WHORequestClientException("There is an account with that email address: " + email)