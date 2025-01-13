package com.safetynet.safetynetalerts;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

@SpringBootApplication
public class SafetynetalertsApplication {

    public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
        SpringApplication.run(SafetynetalertsApplication.class, args);
    }

}
