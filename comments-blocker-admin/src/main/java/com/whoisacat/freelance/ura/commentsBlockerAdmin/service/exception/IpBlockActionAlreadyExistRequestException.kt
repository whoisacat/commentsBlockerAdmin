package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException

class IpBlockActionAlreadyExistRequestException(ip: String) :
    WHOClientRequestException("There is an active block with this ip: $ip")