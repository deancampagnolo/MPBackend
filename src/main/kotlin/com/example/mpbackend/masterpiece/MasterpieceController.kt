package com.example.mpbackend.masterpiece

import com.example.mpbackend.FileStorageService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("api/v1/masterpiece")
class MasterpieceController(
    val masterpieceRepository: MasterpieceRepository,
    val fileStorageService: FileStorageService
) {
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

        masterpieceRepository.save(
            Masterpiece(
                mpContribution.songId,
                mpContribution.title,
                fileStorageService.storeFile(mpContribution.file)
            )
        )
    }

//    @GetMapping("getMasterpiece")
//    fun get(): MasterpieceContribution {
//        val selectedMasterpiece = masterpieceRepository.findById(1).get()
//        val resource = fileStorageService.loadFileAsResource(selectedMasterpiece.pathToAudio)
//        return MasterpieceContribution(selectedMasterpiece.songId, selectedMasterpiece.title, )
//    }

}
