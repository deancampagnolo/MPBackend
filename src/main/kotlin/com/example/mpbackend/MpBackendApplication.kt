package com.example.mpbackend

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.IOException


@SpringBootApplication
@RestController
@RequestMapping("api/v1/customer")
class MpBackendApplication(val studentRepository: StudentRepository) {
    @GetMapping
    fun getCustomers(): List<Student?> {
        return studentRepository.findAll().toList()
    }

    @PutMapping("{id}")
    fun putCustomers(@PathVariable id: String): Unit {
        println(id)
    }

    @Bean
    fun commandLineRunner(studentRepository: StudentRepository): CommandLineRunner? {
        return CommandLineRunner { args: Array<String?>? ->
            val maria = Student("afdsa", "adfses", "madafsnes@afds.edu", 21)
            studentRepository.save(maria)
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//			try {
//				val myObj = File("./audioFS/filename.txt")
//				if (myObj.createNewFile()) {
//					println("File created: " + myObj.name)
//				} else {
//					println("File already exists.")
//				}
//			} catch (e: IOException) {
//				println("An error occurred.")
//				e.printStackTrace()
//			}

            runApplication<MpBackendApplication>(*args)
        }
    }
}
