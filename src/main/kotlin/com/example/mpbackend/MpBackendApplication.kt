package com.example.mpbackend

import com.example.mpbackend.masterpiece.Masterpiece
import com.example.mpbackend.masterpiece.MasterpieceRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*


@CrossOrigin
@SpringBootApplication
@RestController
@RequestMapping("api/v1/default")
class MpBackendApplication(val masterpieceRepository: MasterpieceRepository) {
    @GetMapping("test")
    fun getTest(): String {
        return "test"
    }

    @PutMapping("{id}")
    fun putCustomers(@PathVariable id: String): Unit {
        println(id)
    }

    @Bean
    fun commandLineRunner(
        masterpieceRepository: MasterpieceRepository
    ): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            val bogo = Masterpiece(
                45, "My Piano Track", "MPPianoTrack.mp3, mpDrumTrack.mp3", "Piano, Drums", "guitar, banjo",
                103, "F#", "aRealHoot.mp3", "-25, -10", "{\"16n\": 1}^ {\"16n\": 5}"
            )
            val bogo2 = Masterpiece(
                77, "bogo2", "aRealHoot.mp3", "aRealHoot", "guitar, banjo",
                103, "A#", "aRealHoot.mp3", "-25", "{\"16n\": 1}"
            )
            val bogo3 = Masterpiece(
                34, "bogo3", "aRealHoot.mp3", "aRealHoot", "guitar, banjo",
                103, "B#", "aRealHoot.mp3", "-25", "{\"16n\": 1}"
            )
            masterpieceRepository.save(bogo)
            masterpieceRepository.save(bogo2)
            masterpieceRepository.save(bogo3)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<MpBackendApplication>(*args)
        }
    }
}
