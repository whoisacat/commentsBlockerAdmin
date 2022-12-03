package com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception

import com.whoisacat.freelance.ura.exceptions.WHOClientRequestException

class IpBlockActionNotFoundRequestException() :
    WHOClientRequestException("IP action is not exist")