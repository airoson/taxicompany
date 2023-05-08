package com.javatemplates.taxicompany.forms;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class AdminCarFilter {
    private long id;
    private int size;
    private String name;

    public AdminCarFilter(){
        id = -1;
        name = "";
        size = 10;
    }
}
