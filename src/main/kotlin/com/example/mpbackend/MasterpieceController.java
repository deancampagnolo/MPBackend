package com.example.mpbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/masterpiece")
public class MasterpieceController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("test")
    public String testFun() {
        return "test";
    }
}
