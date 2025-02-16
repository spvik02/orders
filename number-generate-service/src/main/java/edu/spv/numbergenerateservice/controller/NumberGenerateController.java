package edu.spv.numbergenerateservice.controller;

import edu.spv.numbergenerateservice.service.NumberGenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/numbers")
public class NumberGenerateController {

    private final NumberGenerateService numberGenerateService;

    @GetMapping
    public ResponseEntity<String> getNumber() {
        String generatedUniqueNumber = numberGenerateService.generateUniqueNumber();
        return new ResponseEntity<>(generatedUniqueNumber, HttpStatus.OK);
    }
}
