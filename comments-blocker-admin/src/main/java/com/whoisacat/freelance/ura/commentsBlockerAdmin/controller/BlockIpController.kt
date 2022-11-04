package com.whoisacat.freelance.ura.commentsBlockerAdmin.controller

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.BlockPeriod
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.IpBlockActionService
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpBlockActionAlreadyExistException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.IpIsInvalidException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.validation.IpValidator
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class BlockIpController(private val service: IpBlockActionService,
    private val recordsController: IpRecordsController,
    private val ipValidator: IpValidator) {

    @GetMapping("/block/on")
    fun block(model: Model,
        @RequestParam(name = "ip", required = true) ip: String = "",
        @RequestParam(name = "blockPeriod", required = true) blockPeriod: BlockPeriod
    ): String? {
        if (!ipValidator.isValid(ip)) throw IpIsInvalidException(ip)
        try {
            service.blockIp(ip, blockPeriod)
        } catch (e: IpBlockActionAlreadyExistException) {
            return recordsController.findPage(model, ip)
        }
        return recordsController.findPage(model, ip)
    }

    @GetMapping("/block/off")
    fun unblock(model: Model, @RequestParam(name = "id", required = true) id: Long
    ): String? {
        service.unblockIp(id)
        return recordsController.findPage(model, "")
    }

}