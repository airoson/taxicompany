package com.javatemplates.taxicompany.configurations;

import com.javatemplates.taxicompany.models.Photo;
import com.javatemplates.taxicompany.models.User;
import com.javatemplates.taxicompany.models.carmodel.Car;
import com.javatemplates.taxicompany.models.carmodel.Engine;
import com.javatemplates.taxicompany.models.carmodel.Gearbox;
import com.javatemplates.taxicompany.models.carmodel.Rate;
import com.javatemplates.taxicompany.services.CarService;
import com.javatemplates.taxicompany.services.PhotoService;
import com.javatemplates.taxicompany.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Value("${user.admin.email}")
    private String email;
    @Value("${user.admin.password}")
    private String password;
    @Value("${user.admin.telephone}")
    private String telephone;
    @Value("${user.admin.name}")
    private String name;
    @Value("${car.images.max}")
    private int carMaxImages;

    public DatabaseConfig() {
    }

    @Bean
    public CommandLineRunner setCarDatabase(CarService carServices, PhotoService photoService){
        return args -> {
            if(carServices.countAll() < 3){
                File[] cars = (new File(System.getProperty("user.dir") + "/prepared_data")).listFiles();
                for(File carDirectory:cars){
                    log.info("Found new car, file: {}", carDirectory.getAbsolutePath());
                    log.info("All directories: {}", Arrays.toString(carDirectory.listFiles()));
                    List<Photo> photos = new ArrayList<>();
                    File[] carFiles = carDirectory.listFiles();
                    Car car = new Car();
                    int images = 0;
                    for(File carFile:carFiles){
                        if(carFile.getAbsolutePath().endsWith(".txt")){
                            log.info("Working with file {}", carFile.getName());
                            try(FileInputStream file = new FileInputStream(carFile.getAbsolutePath())){
                                String[] lines = new String(file.readAllBytes(), StandardCharsets.UTF_8).split("\n");
                                log.info("Input stream opened, file context: {}", Arrays.toString(lines));
                                for(String line:lines){
                                    String[] params = line.split("=");
                                    params[1] = params[1].replaceAll("[ \r]", "");
                                    if(params[0].equals("цена")) {
                                        log.info(carDirectory.getName() + " price " + params[1]);
                                        try {
                                            System.out.println("params[1] = \"" + params[1] + "\"");
                                            car.setPrice(Integer.parseInt(params[1]));
                                        }catch(Exception e){
                                            log.error(e.getMessage());
                                            throw e;
                                        }
                                    }
                                    else if(params[0].equals("дополнительно")){
                                        if(params.length > 1 && !params[1].isEmpty())
                                            car.setAddons(Arrays.asList(params[1].split("\\|")));
                                    }
                                    else if(params[0].equals("имя"))
                                        car.setName(params[1]);
                                    else if(params[0].equals("Коробка"))
                                        car.setGearbox(Gearbox.fromString(params[1]));
                                    else if(params[0].equals("Топливо"))
                                        car.setEngine(Engine.fromString(params[1]));
                                    else if(params[0].equals("Тарифы")){
                                        List<Rate> rates = new ArrayList<>();
                                        for(String rate: params[1].split("[^а-яА-Я+]"))
                                            if(!rate.isEmpty())
                                                rates.add(Rate.fromString(rate));
                                        log.info("Car " + carDirectory.getName() + " rates " + rates);
                                        car.setRates(rates);
                                    }else if(params[0].equals("Выплаты"))
                                        car.setPayFrequency(Integer.parseInt(params[1]));
                                    else if(params[0].equals("Срок аренды"))
                                        car.setRentalPeriod(Integer.parseInt(params[1]));
                                }
                            }catch(IOException e){
                                log.error(e.getMessage());
                            }
                        }else{
                            if(images < carMaxImages){
                                try(FileInputStream file = new FileInputStream(carFile.getAbsolutePath())){
                                    log.info("Input stream opened, file is image");
                                    Photo photo = new Photo();
                                    photo.setImage(file.readAllBytes());
                                    photoService.addPhoto(photo);
                                    photos.add(photo);
                                }catch(IOException e){
                                    log.error(e.getMessage());
                                }
                                car.setPhotos(photos);
                                images += 1;
                            }
                        }
                    }
                    log.info("Car " + car.getName() + " "+car.getEngine() + " " + car.getGearbox() + " " + car.getPrice() + " added to database.");
                    carServices.addCar(car);
                }
            }
        };
    }

    @Bean
    public CommandLineRunner setUserDatabase(UserService userService, PasswordEncoder passwordEncoder){
        return args -> {
            if(userService.findByPhoneNumber(telephone) == null){
                User admin = new User();
                admin.setEmail(email);
                admin.setPhoneNumber(telephone);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
                admin.setCreatedAt(LocalDateTime.now());
                admin.setName(name);
                userService.addUser(admin);
            }
        };
    }
}
