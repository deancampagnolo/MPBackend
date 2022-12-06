package com.example.mpbackend

import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student?, Long?>