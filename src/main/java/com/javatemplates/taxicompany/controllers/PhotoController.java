package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.models.Photo;
import com.javatemplates.taxicompany.services.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
@Slf4j
public class PhotoController {
    private PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/image/by-id/{id}")
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
}
