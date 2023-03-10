package com.example.mpbackend.masterpiece

data class MPDataContribution(
    val userId: Long,
    val title: String,
    val neededInstruments: List<String>,
    val bpm: Int,
    val key: String,
    val previewSrc: String
)