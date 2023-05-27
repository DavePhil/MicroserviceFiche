package com.foft.microservicefiche;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.foft.microservicefiche")
public class MicroServiceFicheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceFicheApplication.class, args);
    }

}
