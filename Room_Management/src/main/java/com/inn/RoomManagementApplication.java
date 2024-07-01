package com.inn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class RoomManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoomManagementApplication.class, args);
    System.out.println(
        "*********************************************** SERVICE STARTED SUCCESSFULLY ********************************************************");
  }
}
