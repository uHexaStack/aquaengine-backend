package com.qu3dena.aquaengine.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AquaEngineBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquaEngineBackendApplication.class, args);
    }

}
