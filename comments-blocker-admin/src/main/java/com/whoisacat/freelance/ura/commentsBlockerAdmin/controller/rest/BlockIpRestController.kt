package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller.rest

import com.whoisacat.freelance.ura.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpBlockActionService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionAlreadyExistRequestException
import com.whoisacat.freelance.ura.exceptions.IpIsInvalidRequestException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.validation.IpValidator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BlockIpRestController(private val service: IpBlockActionService,
                            private val ipValidator: IpValidator) {

    @PostMapping("/api/block/{ip}/on")
    fun block(@PathVariable(name = "ip", required = true) ip: String = "",
        @RequestParam(name = "blockPeriod", required = true) blockPeriod: BlockPeriod
    ): ResponseEntity<Void> {
        if (!ipValidator.isValid(ip)) throw IpIsInvalidRequestException(ip)
        service.blockIp(ip, blockPeriod)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/api/block/off")
    fun unblock(@RequestParam(name = "id", required = true) id: Long): ResponseEntity<Void> {
        service.unblockIp(id)
        return ResponseEntity.noContent().build()
    }
}
