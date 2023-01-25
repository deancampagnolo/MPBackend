package com.example.mpbackend.s3

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("api/v1/s3Files")
class S3FilesController {
    @Autowired
    var s3FilesService: S3FilesService? = null

    @GetMapping("test")
    fun testFun(): String {
        return "test"
    }

    @GetMapping("urlGet/{fileNames}")
    fun findByName(@PathVariable("fileNames") fileNames: List<String>): ResponseEntity<List<String>> {
        val urlGetList: MutableList<String> = mutableListOf()

        s3FilesService?.let { s3 ->
            fileNames.forEach { fileName ->
                urlGetList.add(s3.findByName(fileName))
            }
        }

        return ResponseEntity(urlGetList, HttpStatus.OK)
    }

    // Returns a list containing new file name and signed link to post
    // TODO: Wrap it in an object or something to make it neater
    @GetMapping("urlPostMp3s/{num}")
    fun saveFile(@PathVariable num: Long): ResponseEntity<Any> {
        val urlPostList: MutableList<String> = mutableListOf()

        s3FilesService?.let { s3 ->
            for (i in 1..num) {
                urlPostList.addAll(s3.save(".mp3"))
            }
        }

        if (s3FilesService == null) {
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return ResponseEntity(urlPostList, HttpStatus.OK)
    }
//
//    @GetMapping("urlPost")
//    fun saveFile(@RequestParam("extensionList") extensionList: List<String>): ResponseEntity<Any> {
//        val urlPostList: MutableList<String> = mutableListOf()
//
//        s3FilesService?.let { s3 ->
//            extensionList.forEach { extension ->
//                urlPostList.add(s3.save(extension))
//            }
//        }
//
//        if (s3FilesService == null) {
//            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
//        }
//
//        return ResponseEntity(urlPostList, HttpStatus.OK)
//    }
}