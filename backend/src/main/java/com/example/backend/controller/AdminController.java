package com.example.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.stream.Collectors;
    @RestController
    @RequestMapping("/admin")
    public class AdminController {

        @Autowired
        private RequestMappingHandlerMapping handlerMapping;

        @GetMapping("/routes")
        public Object listRoutes() {
            return handlerMapping.getHandlerMethods()
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey().toString() + " => " + entry.getValue().getMethod().toString())
                    .collect(Collectors.toList());
        }
    }