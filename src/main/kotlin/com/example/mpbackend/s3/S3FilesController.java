package com.example.mpbackend.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/s3Files")
public class S3FilesController {
    @Autowired
    S3FilesService s3FilesService;

    @GetMapping("urlGet/{fileName:.+}")
    public ResponseEntity<Object> findByName(@PathVariable String fileName) {
        return new ResponseEntity<>(s3FilesService.findByName(fileName), HttpStatus.OK);
    }

    @GetMapping("urlPost/{extension}")
    public ResponseEntity<Object> saveFile(@PathVariable String extension) {
        return new ResponseEntity<>(s3FilesService.save(extension), HttpStatus.OK);
    }

}
