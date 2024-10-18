package com.example.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DatabaseController {
//    @Autowired
//    private UserRepository userRepository;

    @GetMapping(path="/", produces = "application/json")
    public Map<String,String> getRoot(){

        Map<String, String> response = new HashMap<>();
        response.put("message","ok");
        response.put( "status","active");
        return response;
    }
}
