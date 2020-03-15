package com.rahuldeepattri.poc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;

@ServletComponentScan(basePackages = "com.rahuldeepattri.poc.configurations")
@SpringBootApplication
public class CaMicroscopeChallengeApplication {

    public static void main(String[] args) {
        ImageIO.scanForPlugins();
        SpringApplication.run(CaMicroscopeChallengeApplication.class, args);
    }

}
