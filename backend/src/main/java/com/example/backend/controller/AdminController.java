package com.example.backend.controller;

import com.example.backend.config.ApiPaths;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.stream.Collectors;
    @RestController
    @RequestMapping(ApiPaths.ADMIN_SERVICE)
    public class AdminController {

        @Autowired
        private RequestMappingHandlerMapping handlerMapping;

        @GetMapping(ApiPaths.ROUTES)
        public Object listRoutes() {
            return handlerMapping.getHandlerMethods()
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey().toString() + " => " + entry.getValue().getMethod().toString())
                    .collect(Collectors.toList());
        }
    }