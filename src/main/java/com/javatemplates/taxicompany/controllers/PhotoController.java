package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.models.Photo;
import com.javatemplates.taxicompany.services.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class PhotoController {
    private PhotoService photoService;
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/image/{id}")
    public void getImage(@PathVariable Long id, HttpServletResponse response){
        response.setContentType("image/png");
        Photo photo = photoService.findPhotoById(id);
        if(photo != null) {
            try{
                response.getOutputStream().write(photo.getImage());
            }catch(IOException e){
                log.error(e.getMessage());
            }
        }
    }

    @PutMapping("/image")
    public ResponseEntity<Photo> putPhoto(@RequestParam MultipartFile file){
        Photo photo = new Photo();
        try{
            photo.setImage(file.getBytes());
        }catch(IOException e){
            log.error(e.getMessage());
        }
        photoService.addPhoto(photo);
        log.info("Saved new Photo, id = {}", photo.getId());
        if(photo.getImage() == null)
            log.error("Somehow image is null");
        return new ResponseEntity<Photo>(photo, HttpStatus.OK);
    }
}
