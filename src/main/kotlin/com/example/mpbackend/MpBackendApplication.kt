package com.example.mpbackend

import com.example.mpbackend.masterpiece.Masterpiece
import com.example.mpbackend.masterpiece.MasterpieceRepository
import com.example.mpbackend.masterpiece.UserDetails
import com.example.mpbackend.masterpiece.UserDetailsRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*


@CrossOrigin
@SpringBootApplication
@RestController
@RequestMapping("api/v1/default")
class MpBackendApplication(
    val masterpieceRepository: MasterpieceRepository,
    val userDetailsRepository: UserDetailsRepository
) {
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
        masterpieceRepository: MasterpieceRepository, userDetailsRepository: UserDetailsRepository
    ): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            val bob = UserDetails(12)
            val george = UserDetails(14)
            val bogo = Masterpiece(
                bob.userId,
                System.currentTimeMillis(),
                "My Piano Track",
                "MPPianoTrack.mp3, mpDrumTrack.mp3",
                "Piano, Drums",
                "guitar, banjo",
                103,
                "F#",
                "aRealHoot.mp3",
                "-25, -10",
                "{\"16n\": 1}^ {\"16n\": 5}"
            )
            //bogo.userContributions.add(bob)
            val bogo2 = Masterpiece(
                bob.userId, System.currentTimeMillis(), "bogo2", "aRealHoot.mp3", "aRealHoot", "guitar, banjo",
                103, "A#", "aRealHoot.mp3", "-25", "{\"16n\": 1}"
            )
            //bogo2.userContributions.add(bob)
            val bogo3 = Masterpiece(
                george.userId, System.currentTimeMillis(), "bogo3", "aRealHoot.mp3", "aRealHoot", "guitar, banjo",
                103, "B#", "aRealHoot.mp3", "-25", "{\"16n\": 1}"
            )
            //bogo2.userContributions.add(george)
            userDetailsRepository.save(bob)
            userDetailsRepository.save(george)
            masterpieceRepository.save(bogo)
            masterpieceRepository.save(bogo2)
            masterpieceRepository.save(bogo3)
            val mp = masterpieceRepository.findById(1).get()
            val ud = userDetailsRepository.findById(12).get()
            mp.userContributions.add(ud)
            mp.userContributions.add(george)
            masterpieceRepository.save(mp)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<MpBackendApplication>(*args)
        }
    }
}
