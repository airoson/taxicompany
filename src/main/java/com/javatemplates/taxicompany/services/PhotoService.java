package com.javatemplates.taxicompany.services;

import com.javatemplates.taxicompany.models.Photo;
import com.javatemplates.taxicompany.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    private PhotoRepository photoRepository;

    public PhotoService(PhotoRepository repository) {
        this.photoRepository = repository;
    }

    public Photo findPhotoById(Long id){
        return photoRepository.findById(id).orElse(null);
    }

    public void addPhoto(Photo p){
        photoRepository.save(p);
    }
}
