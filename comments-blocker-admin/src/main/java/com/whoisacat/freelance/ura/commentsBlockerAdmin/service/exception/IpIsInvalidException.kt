package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

class IpIsInvalidException(ip: String) :
    WHORequestClientException("IP is invalid: $ip")