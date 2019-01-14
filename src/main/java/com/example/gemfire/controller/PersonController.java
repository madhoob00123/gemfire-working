package com.example.gemfire.controller;


import com.example.gemfire.repository.PersonRepository;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PersonController {




    @GetMapping("test")

    public String test(){


        return "Hello world";
    }

}
