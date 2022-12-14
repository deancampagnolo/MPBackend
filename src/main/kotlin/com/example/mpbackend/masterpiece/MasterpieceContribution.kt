package com.example.mpbackend.masterpiece

import org.springframework.web.multipart.MultipartFile

data class MasterpieceContribution(val songId: Long, val title: String, val file: MultipartFile)