package com.shop;

import com.javaquasar.util.desktop.Browser;
import com.shop.repository.entity.Person;
import com.shop.repository.jdbc.PersonJbdcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.Date;

//@SpringBootApplication
public class ApplicationJdbc implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PersonJbdcDao dao;

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(ApplicationJdbc.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        System.out.println("Application started ... launching browser now");
        Browser.browse("http://localhost:8080/");
        Browser.browse("http://localhost:8080/h2-console/");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");
        logger.info("All users -> {}", dao.findAll());

        logger.info("User id 10001 -> {}", dao.findById(10001));

//        logger.info("Deleting 10002 -> No of Rows Deleted - {}",
//                dao.deleteById(10002));

        logger.info("Inserting 10004 -> {}",
                dao.insert(new Person(10004, "Tara", "Berlin", new Date())));

        logger.info("Update 10003 -> {}",
                dao.update(new Person(10003, "Pieter", "Utrecht", new Date())));
    }
}
