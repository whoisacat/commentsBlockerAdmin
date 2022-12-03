package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException

class IpRecordNotFoundExistRequestException() :
    WHOClientRequestException("Ip not found")