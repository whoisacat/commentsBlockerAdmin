package com.whoisacat.freelance.ura.commentsBlockerAdmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class CommentsBlockerAdmin

fun main(args: Array<String>) {
	runApplication<CommentsBlockerAdmin>(*args)
}
