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
@RequestMapping("api/v1/customer")
class MpBackendApplication(val studentRepository: StudentRepository, val masterpieceRepository: MasterpieceRepository) {
    @GetMapping
    fun getCustomers(): List<Student?> {
        return studentRepository.findAll().toList()
    }

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
        studentRepository: StudentRepository,
        masterpieceRepository: MasterpieceRepository
    ): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            val maria = Student("afdsa", "adfses", "madafsnes@afds.edu", 21)
            studentRepository.save(maria)
            val bogo = Masterpiece(1, "bogo", "peebpoob/dfs")
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
