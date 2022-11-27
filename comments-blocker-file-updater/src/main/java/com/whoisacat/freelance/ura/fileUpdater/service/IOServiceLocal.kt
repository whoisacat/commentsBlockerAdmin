package com.whoisacat.freelance.ura.fileUpdater.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.*


@Service
class IOServiceLocal(@Value("\${com.whoisacat.commentsBlocker.service.fileName}") val fileName: String)
    : IOService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun appendLine(string: String) {
        try {
            val bw = BufferedWriter(FileWriter(fileName))
            bw.append(string)
            bw.flush()
            bw.close()
        } catch (ioe: IOException) {
            logger.error(this.javaClass.simpleName + ioe.toString())
        }
    }

    override fun replaceContent(content: String) {
        try {
            val bw = BufferedWriter(FileWriter(fileName))
            bw.write(content)
            bw.flush()
            bw.close()
        } catch (ioe: IOException) {
            logger.error(this.javaClass.simpleName + ioe.toString())
        }
    }

    override fun readFile(): String {
        val dir = File(fileName.run {
            val arr = split("/")
            val localFileName = arr[arr.size - 1]
            replace(localFileName, "")
        })
        if (!(dir.exists() || dir.mkdirs())) throw NoSuchFileException(dir)
        val file = File(fileName)
        if (!(file.exists() || file.createNewFile())) throw NoSuchFileException(file)
        val br = BufferedReader(FileReader(fileName))
        var text = ""
        var s: String?
        while (br.readLine().also { s = it } != null) {
            text = text + s + "\n"
        }
        br.close()
        return text
    }
}