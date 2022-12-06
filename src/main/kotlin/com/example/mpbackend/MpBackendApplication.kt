package com.example.mpbackend

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customer")
class MpBackendApplication(val studentRepository: StudentRepository) {
	@GetMapping
	fun getCustomers(): List<Student> {
		return studentRepository.findAll()
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
			runApplication<MpBackendApplication>(*args)
		}
	}
}
