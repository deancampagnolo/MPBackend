package com.example.mpbackend.masterpiece

import org.springframework.data.jpa.repository.JpaRepository

interface MasterpieceRepository : JpaRepository<Masterpiece?, Long?> {
    fun findByTitle(title: String): List<Masterpiece?>

    fun findTopByOrderByUserIdDesc(): Masterpiece?
}