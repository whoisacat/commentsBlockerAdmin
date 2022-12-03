package com.whoisacat.freelance.ura.exceptions

class IpIsInvalidRequestException(ip: String) :
    WHOClientRequestException("IP is invalid: $ip")