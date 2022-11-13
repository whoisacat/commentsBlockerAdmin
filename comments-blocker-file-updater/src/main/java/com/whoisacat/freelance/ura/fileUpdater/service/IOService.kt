package com.whoisacat.freelance.ura.fileUpdater.service

interface IOService {
    fun appendLine(string: String)
    fun readFile(): String
    fun replaceContent(content: String)
}