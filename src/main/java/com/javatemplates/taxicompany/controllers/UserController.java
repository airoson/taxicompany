package com.javatemplates.taxicompany.controllers;

import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    private UserService userService;
    private PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<?> putUser(@RequestBody User user){
        if(user.getPhoneNumber() != null && userService.findByPhoneNumber(user.getPhoneNumber()) != null &&
        user.getName() != null && user.getAuthorities() != null){
            User added = new User();
            added.setRoles(user.getRoles());
            added.setEmail(user.getEmail());
            added.setName(user.getName());
            added.setPassword(encoder.encode(user.getPassword()));
            added.setPhoneNumber(user.getPhoneNumber());
            added.setCreatedAt(LocalDateTime.now());
            userService.addUser(added);
            return new ResponseEntity<>(String.format("{\"id\": %d}", added.getId()), HttpStatus.OK);
        }else return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userService.findById(id);
        if(user != null)
            return ResponseEntity.ok().body(user);
        else return ResponseEntity.notFound().build();
    }
}
