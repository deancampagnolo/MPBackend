package com.example.mpbackend.masterpiece

import com.example.mpbackend.localstorage.FileController
import com.example.mpbackend.localstorage.FileStorageService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

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
    fun postMasterpiece(mpContribution: MPBackendContribution) {

        masterpieceRepository.save(
            Masterpiece(
                mpContribution.songId,
                mpContribution.title,
                mpContribution.fileNames.joinToString { it }
//                fileStorageService.storeFile(mpContribution.file)
            )
        )
    }

    @GetMapping("getResource")
    fun getResource(request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = fileStorageService.loadFileAsResource("9to5.mp3")
        return FileController.getResponseEntity(resource, request)
    }

    @GetMapping("getMasterpieceData/{id}")
    fun getMasterpieceData(@PathVariable id: Long): MPClientContribution? {
        val selectedMasterpiece = masterpieceRepository.findById(id).get()

        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
            MPClientContribution(
                selectedMasterpiece.songId!!,
                selectedMasterpiece.title!!,
                selectedMasterpiece.pathsToAudio!!.split(",")
            )
        } else {
            null
        }
    }

    @GetMapping("getRandomMasterpieceData")
    fun getRandomMasterpieceData(): MPClientContribution? {
        val selectedMasterpiece = masterpieceRepository.findById(1).get()

        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
            MPClientContribution(
                selectedMasterpiece.songId!!,
                selectedMasterpiece.title!!,
                selectedMasterpiece.pathsToAudio!!.split(",")
            )
        } else {
            null
        }
    }
}
