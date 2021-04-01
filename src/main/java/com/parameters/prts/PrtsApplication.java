package com.parameters.prts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.vaadin.artur.helpers.LaunchUtil;

@SpringBootApplication()
public class PrtsApplication {

    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(PrtsApplication.class, args));
//        SpringApplication.run(PrtsApplication.class, args);
        System.out.println("hi");
    }
}
