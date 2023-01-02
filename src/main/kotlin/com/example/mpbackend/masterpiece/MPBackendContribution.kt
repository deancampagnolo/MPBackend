package com.example.mpbackend.masterpiece

import org.springframework.web.multipart.MultipartFile

data class MPBackendContribution(val songId: Long, val title: String, val fileName: String)