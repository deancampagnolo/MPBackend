package com.example.mpbackend.masterpiece

import com.example.mpbackend.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/masterpiece")
class MasterpieceController(val masterpieceRepository: MasterpieceRepository, val fileStorageService: FileStorageService) {
    @GetMapping("test")
    fun testFun(): String {
        return "test"
    }

    @GetMapping("getAll")
    fun getAll(): List<Masterpiece?> {
        return masterpieceRepository.findAll().toList()
    }

    @PostMapping("postMasterpiece")
    fun postMasterpiece(mpContribution: MasterpieceContribution) {
        println(mpContribution)
        fileStorageService.storeFile(mpContribution.file)
        masterpieceRepository.save(
            Masterpiece(
                mpContribution.songId,
                mpContribution.title,
                "placeholder"
            )
        );
    }
}
