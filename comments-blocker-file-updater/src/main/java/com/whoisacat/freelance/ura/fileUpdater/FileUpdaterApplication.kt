package com.whoisacat.freelance.ura.fileUpdater

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FileUpdaterApplication

fun main(args: Array<String>) {
	runApplication<FileUpdaterApplication>(*args)
}
