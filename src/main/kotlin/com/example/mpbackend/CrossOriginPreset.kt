package com.example.mpbackend

import org.springframework.web.bind.annotation.CrossOrigin

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000", "https://masterpiecemusic.io", "https://famous-mandazi-31dc6a.netlify.app/", "https://main--famous-mandazi-31dc6a.netlify.app/"])
annotation class CrossOriginPreset

