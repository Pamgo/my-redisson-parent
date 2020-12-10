package com.okay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

@SpringBootApplication
//@EnableCaching
public class MyredissonApp {
    public static void main( String[] args ){
        SpringApplication.run(MyredissonApp.class, args);
    }
}
