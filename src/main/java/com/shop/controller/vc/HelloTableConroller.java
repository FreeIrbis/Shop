package com.shop.controller.vc;

import com.shop.db.entity.Person;
import com.shop.db.jpa.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HelloTableConroller {

    //@Autowired
    //PersonJpaRepository repository;

    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public String table(Model model) {
        //List<Person> persons = repository.findAll();
        List<Person> persons = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            Person person = new Person();
            person.setId(i);
            person.setName("John " + i);
            person.setBirthDate(new Date());
            person.setLocation("Sity " + i);
            persons.add(person);
        }
        model.addAttribute("persons", persons);
        return "/hello";
    }
}