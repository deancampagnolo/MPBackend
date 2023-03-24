package com.example.mpbackend.masterpiece

import com.example.mpbackend.CrossOriginPreset
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.*
import kotlin.properties.Delegates

@CrossOriginPreset
@RestController
@EnableJpaRepositories
@RequestMapping("api/v1/masterpiece")
class MasterpieceController(
    val masterpieceRepository: MasterpieceRepository,
    val userDetailsRepository: UserDetailsRepository
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
    }

    private fun getUser(id: String): UserDetails {
        var user = userDetailsRepository.findById(id).orElse(null)
        if (user == null) {
            user = userDetailsRepository.save(UserDetails(id))
        }
        return user
    }

    @PostMapping("postMasterpiece")
    fun postMasterpiece(@RequestBody mpContribution: MPBackendContribution): Long? {
        println(mpContribution)
        val srcFiles: MutableList<String> = mutableListOf()
        val snippetTitles: MutableList<String> = mutableListOf()
        val volumes: MutableList<String> = mutableListOf()
        val nudgeObjects: MutableList<String> = mutableListOf()
        val data = mpContribution.dataContribution
        val user = getUser(data.userId)
        mpContribution.snippetContributions.forEach {
            srcFiles.add(it.src)
            snippetTitles.add(it.snippetTitle)
            volumes.add(it.volume.toString())
            nudgeObjects.add(it.nudgeAmountObject)
        }

        val newMasterpiece = Masterpiece(
            user.userId,
            System.currentTimeMillis(),
            data.title,
            srcFiles.joinToString { it },
            snippetTitles.joinToString { it },
            data.neededInstruments.joinToString { it },
            data.bpm,
            data.key,
            data.previewSrc,
            volumes.joinToString { it },
            nudgeObjects.joinToString(separator = "^ ") { it }
        )
        newMasterpiece.userContributions.add(user)

        return masterpieceRepository.save(newMasterpiece).songId
    }

    @GetMapping("getMasterpieceData/{id}")
    fun getMasterpieceData(@PathVariable id: Long): MPBackendContribution? {
        val selectedMasterpiece = masterpieceRepository.findById(id).get()

        val snippetContributions: MutableList<MPSnippetContribution> = mutableListOf()
        val pathsToAudio = selectedMasterpiece.pathsToAudio!!.split(", ")
        val snippetTitles = selectedMasterpiece.snippetTitles!!.split(", ")
        val volumes = selectedMasterpiece.volumes!!.split(", ").map { it.toFloat() }
        val neededInstruments = selectedMasterpiece.neededInstruments!!.split(", ")
        val nudgeObjects = selectedMasterpiece.nudgeObjects!!.split("^ ")
        pathsToAudio.forEachIndexed { index, _ ->
            snippetContributions.add(
                MPSnippetContribution(
                    pathsToAudio[index],
                    snippetTitles[index],
                    volumes[index],
                    nudgeObjects[index]
                )
            )
        }

        return if (selectedMasterpiece.title != null && selectedMasterpiece.pathsToAudio != null) {
            MPBackendContribution(
                MPDataContribution(
                    selectedMasterpiece.userId!!,
                    selectedMasterpiece.title!!,
                    neededInstruments,
                    selectedMasterpiece.bpm!!,
                    selectedMasterpiece.key!!,
                    selectedMasterpiece.previewUrl!!,
                ),
                snippetContributions,
            )
        } else {
            null
        }
    }

    @GetMapping("getMasterpieceTitle/{id}")
    fun getMasterpieceTitle(@PathVariable id: Long): String? {
        return masterpieceRepository.findById(id).get().title
    }

    private fun generateRandomIndex(): Long {
        return (1..masterpieceRepository.count()).random()
    }

    @RequestMapping(
        value = ["getRandomMasterpieceId/", "getRandomMasterpieceId/{userId}"],
        method = [RequestMethod.GET]
    )
    fun getRandomMasterpieceId(@PathVariable(required = false) userId: String?): Long {
        if (userId == null) {
            return generateRandomIndex()
        }
        val user = getUser(userId)
        var index by Delegates.notNull<Long>()
        for (i in 1..10) {
            index = generateRandomIndex()
            if (!user.visitedMasterpieces.contains(index)) {
                break
            }
        }
        user.visitedMasterpieces.add(index)
        userDetailsRepository.save(user)
        return index
    }

    // FIXME Double check if its getting the 10 most recent
    @GetMapping("getAllMasterpieceHistory/{userId}/{page}/{amount}")
    fun getAllMasterpieceHistory(
        @PathVariable userId: String,
        @PathVariable page: Int,
        @PathVariable amount: Int
    ): List<Long> {
        return getUser(userId).masterpieces.drop(page * amount).take(amount).mapNotNull { it.songId }
    }
}
