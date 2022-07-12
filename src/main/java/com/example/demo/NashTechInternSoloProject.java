package com.example.demo;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.HashMap;
import java.util.Map;

@EnableJpaAuditing
@SpringBootApplication
public class NashTechInternSoloProject {
    public static void main(String[] args) {
        SpringApplication.run(NashTechInternSoloProject.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary;
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dvse1dwgn");
        config.put("api_key", "281126638627698");
        config.put("api_secret", "LihZfizgvpml1gulkM7-wDvudrQ");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
