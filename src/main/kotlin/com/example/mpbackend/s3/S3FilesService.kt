package com.example.mpbackend.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ResponseHeaderOverrides
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
        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(s3BucketName, fileName, httpMethod)
        val responseHeaders = ResponseHeaderOverrides() // I do not understand why this has to be a response header
        // rather than setting the content type on generatePresignedUrlRequest directly... if the latter, then I get a 403 error from client
        // saying 'The request signature we calculated does not match the signature you provided. Check your key and signing method.'
        // I did notice when I put it into postman that with the latter method it generates a put url where the response content type
        // is not audio related. idk why its response content type instead of content type if I am directly uploading the file though
        // and not getting it through a response
        responseHeaders.contentType = "audio/mpeg" // FIXME Not coding the ogg files correctly
        generatePresignedUrlRequest.responseHeaders = responseHeaders;
        // generatePresignedUrlRequest.contentType = "audio/mpeg" // TODO this should eventually be implemented, right now there is a bug in frontend erroring when this is used, def fixable - just a bit lazy

        return amazonS3!!.generatePresignedUrl(generatePresignedUrlRequest).toString()
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