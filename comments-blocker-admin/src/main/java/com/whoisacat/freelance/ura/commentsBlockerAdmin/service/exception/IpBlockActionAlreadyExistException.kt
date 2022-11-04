package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

class IpBlockActionAlreadyExistException(ip: String) :
    WHORequestClientException("There is an active block with this ip: $ip")