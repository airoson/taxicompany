package com.javatemplates.taxicompany.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactForm {
    private String name, phoneNumber, driverLicense;
}
