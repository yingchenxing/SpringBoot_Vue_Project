package com.example.managesystem;

import com.example.managesystem.entity.User;
import com.example.managesystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class ManageSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(ManageSystemApplication.class, args);
    }




}
