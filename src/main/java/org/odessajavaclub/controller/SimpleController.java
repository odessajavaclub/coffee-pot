package org.odessajavaclub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/test")
    public ResponseEntity<String> getTestResult() {
        return ResponseEntity.ok("Success");
    }

}
