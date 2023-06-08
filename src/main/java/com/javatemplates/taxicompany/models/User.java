package com.javatemplates.taxicompany.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.javatemplates.taxicompany.models.carmodel.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;
    @Convert(converter = UserRolesConverter.class)
    private List<String> roles;

    @OneToMany(fetch=FetchType.LAZY)
    private List<Car> favorites;

    @JsonProperty("favorites")
    private void unpackNested(List<Object> favoritesJsonList){
        favorites = new ArrayList<>();
        for(Object o: favoritesJsonList){
            Map<String, Integer> favorite = (Map<String, Integer>)o;
            Car car = new Car();
            car.setId(favorite.get("id").longValue());
            favorites.add(car);
        }
    }

    public boolean isInFavorite(Long id){
        for(Car car: favorites){
            if(car.getId().equals(id))
                return true;
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
