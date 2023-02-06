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

    @PostMapping("postMPTest")
    fun postMPTest(@RequestBody mpContribution: MPSnippetContribution): Unit {
        println(mpContribution)
    }

    @PostMapping("postMasterpiece")
    fun postMasterpiece(@RequestBody mpContribution: MPBackendContribution): Long? {
        println(mpContribution)
        val srcFiles: MutableList<String> = mutableListOf()
        val snippetTitles: MutableList<String> = mutableListOf()
        mpContribution.snippetContributions.forEach {
            srcFiles.add(it.src)
            snippetTitles.add(it.snippetTitle)
        }
        return masterpieceRepository.save(
            Masterpiece(
                mpContribution.songId,
                mpContribution.title,
                srcFiles.joinToString { it },
                snippetTitles.joinToString { it },
            )
        ).userId
    }

    @GetMapping("getResource")
    fun getResource(request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = fileStorageService.loadFileAsResource("9to5.mp3")
        return FileController.getResponseEntity(resource, request)
    }

    @GetMapping("getMasterpieceData/{id}")
    fun getMasterpieceData(@PathVariable id: Long): MPBackendContribution? {
        val selectedMasterpiece = masterpieceRepository.findById(id).get()

        val snippetContributions: MutableList<MPSnippetContribution> = mutableListOf()
        val pathsToAudio = selectedMasterpiece.pathsToAudio!!.split(", ")
        val snippetTitles = selectedMasterpiece.snippetTitles!!.split(", ")
        pathsToAudio.forEachIndexed { index, _ ->
            snippetContributions.add(MPSnippetContribution(pathsToAudio[index], snippetTitles[index]))
        }

        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
            MPBackendContribution(
                selectedMasterpiece.songId!!,
                selectedMasterpiece.title!!,
                snippetContributions,
            )
        } else {
            null
        }
    }

//    @GetMapping("getRandomMasterpieceData")
//    fun getRandomMasterpieceData(): MPClientContribution? {
//        val selectedMasterpiece = masterpieceRepository.findById(1).get()
//
//        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
//            MPClientContribution(
//                selectedMasterpiece.songId!!,
//                selectedMasterpiece.title!!,
//                selectedMasterpiece.pathsToAudio!!.split(", ")
//            )
//        } else {
//            null
//        }
//    }
}
