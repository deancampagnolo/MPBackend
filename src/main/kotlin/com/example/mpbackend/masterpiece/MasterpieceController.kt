package com.example.mpbackend.masterpiece

import com.example.mpbackend.localstorage.FileController
import com.example.mpbackend.localstorage.FileStorageService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
                mpContribution.fileName
//                fileStorageService.storeFile(mpContribution.file)
            )
        )
    }

    @GetMapping("getResource")
    fun getResource(request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = fileStorageService.loadFileAsResource("9to5.mp3")
        return FileController.getResponseEntity(resource, request)
    }

    @GetMapping("getMasterpieceData")
    fun getMasterpieceData(): MPClientContribution? {
        val selectedMasterpiece = masterpieceRepository.findById(1).get()

        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathToAudio != null) {
//            val resource = fileStorageService.loadFileAsResource(selectedMasterpiece.pathToAudio)
            MPClientContribution(
                selectedMasterpiece.songId!!,
                selectedMasterpiece.title!!,
                listOf("9to6.mp3", "abc.wav")
            )
        } else {
            null
        }
    }

}
