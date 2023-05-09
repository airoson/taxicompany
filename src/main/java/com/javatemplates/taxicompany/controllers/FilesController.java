package com.javatemplates.taxicompany.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController()
@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/files/")
public class FilesController {
    @Value("${file.offer.name}")
    private String offerFileName;
    @GetMapping("{file}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String file){
        if(file.equals("offer")){
            String path = System.getProperty("user.dir") + "/files/" + offerFileName;
            try{
                String mime = java.nio.file.Files.probeContentType((new File(path)).toPath()) + "; charset=utf-8";
                InputStream fileStream = new FileInputStream(path);
                log.info("Successfully returning offer file {}", offerFileName);
                return ResponseEntity.ok().contentType(MediaType.valueOf(mime)).body(new InputStreamResource(fileStream));
            }catch(IOException e){
                log.error(e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }
        log.info("File with name {} not found", file);
        return ResponseEntity.notFound().build();
    }
}
