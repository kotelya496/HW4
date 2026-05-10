package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HW4Application {

    public static void main(String[] args) {
        SpringApplication.run(HW4Application.class,args);
        ConsoleClient.showMenu();
    }

}
