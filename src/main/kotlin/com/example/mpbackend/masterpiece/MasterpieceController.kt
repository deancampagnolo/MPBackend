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
        val volumes: MutableList<String> = mutableListOf()
        val data = mpContribution.dataContribution
        mpContribution.snippetContributions.forEach {
            srcFiles.add(it.src)
            snippetTitles.add(it.snippetTitle)
            volumes.add(it.volume.toString())
        }
        return masterpieceRepository.save(
            Masterpiece(
                data.songId,
                data.title,
                srcFiles.joinToString { it },
                snippetTitles.joinToString { it },
                data.neededInstruments.joinToString { it },
                data.bpm,
                data.key,
                volumes.joinToString { it },
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
        val volumes = selectedMasterpiece.volumes!!.split(", ").map { it.toFloat() }
        val neededInstruments = selectedMasterpiece.neededInstruments!!.split(", ")
        pathsToAudio.forEachIndexed { index, _ ->
            snippetContributions.add(MPSnippetContribution(pathsToAudio[index], snippetTitles[index], volumes[index]))
        }

        return if (selectedMasterpiece.songId != null && selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
            MPBackendContribution(
                MPDataContribution(
                    selectedMasterpiece.songId!!,
                    selectedMasterpiece.title!!,
                    neededInstruments,
                    selectedMasterpiece.bpm!!,
                    selectedMasterpiece.key!!,
                ),
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
