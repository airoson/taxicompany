package com.javatemplates.taxicompany.security;

import com.javatemplates.taxicompany.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RegistrationForm {
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    public User toUser(PasswordEncoder encoder){
        if(password == null)
            log.error("Somehow password is null");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        String encoded = encoder.encode(password);
        log.info("Encoded password " + encoded + ", given " + password);
        user.setPassword(encoded);
        user.setRoles(Arrays.asList("ROLE_USER"));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
