package com.example.mpbackend.masterpiece

import org.springframework.data.jpa.repository.JpaRepository

interface UserDetailsRepository : JpaRepository<UserDetails?, String?> {

}