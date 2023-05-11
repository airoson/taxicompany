package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @PutMapping()
    public ResponseEntity<?> putUser(@RequestBody User user){
        if(userService.findByPhoneNumber(user.getPhoneNumber()) != null &&
        user.getName() != null && user.getAuthorities() != null && user.getEmail() != null){
            userService.addUser(user);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else return ResponseEntity.unprocessableEntity().build();
    }
}
