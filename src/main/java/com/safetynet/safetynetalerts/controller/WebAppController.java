package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAppController {

    // handles GET requests from web app and calls Services' methods

    @GetMapping("/")
    public String string() {
        return "";
    }

}
