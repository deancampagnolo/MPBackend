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
            val bogo = Masterpiece(1, "bogo", "JerryToes.mp3", "Jerrys Toes", "guitar, banjo", 103, "F#", "-25")
            masterpieceRepository.save(bogo)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<MpBackendApplication>(*args)
        }
    }
}
