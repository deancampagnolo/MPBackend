package com.example.mpbackend.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.*

@Service
class S3FilesService {
    @Autowired
    private val amazonS3: AmazonS3? = null

    @Value("masterpiecepublic") //@Value("meow")

    private val s3BucketName: String? = null
    private fun generateUrl(fileName: String, httpMethod: HttpMethod): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1) // Generated URL will be valid for 24 hours
        return amazonS3!!.generatePresignedUrl(s3BucketName, fileName, calendar.time, httpMethod).toString()
    }

    @Async
    fun findByName(fileName: String): String {
        if (!amazonS3!!.doesObjectExist(s3BucketName, fileName)) return "File does not exist"
        LOG.info("Generating signed URL for file name {}", fileName)
        return generateUrl(fileName, HttpMethod.GET)
    }

    @Async
    fun save(extension: String): List<String> {
        val fileName = UUID.randomUUID().toString() + extension
        return listOf(fileName, generateUrl(fileName, HttpMethod.PUT))
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(S3FilesService::class.java)
    }
}