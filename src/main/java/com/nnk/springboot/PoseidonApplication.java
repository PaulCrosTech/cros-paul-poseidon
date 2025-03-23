package com.nnk.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PoseidonApplication Class
 */
@SpringBootApplication
@Slf4j
public class PoseidonApplication {

    /**
     * Main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
        log.info("====> Application Poseidon started <====");
    }

}
