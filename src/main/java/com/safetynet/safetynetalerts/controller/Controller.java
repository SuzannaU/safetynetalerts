package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Controller {

    @GetMapping

    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

    @PutMapping("path/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        // TODO: process PUT request

        return entity;
    }

    @DeleteMapping
    public void deletePerson() {

    }

}
