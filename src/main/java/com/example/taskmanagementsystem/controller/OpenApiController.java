package com.example.taskmanagementsystem.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    private final ResourceLoader resourceLoader;

    public OpenApiController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/v3/api-docs.yaml")
    public Resource getOpenApiYaml() {
        return resourceLoader.getResource("classpath:openapi.yaml");
    }
}