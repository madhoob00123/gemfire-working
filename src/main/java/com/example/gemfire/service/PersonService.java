package com.example.gemfire.service;


import com.example.gemfire.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {


    private final PersonRepository personrepo;

    @Autowired
    public PersonService(PersonRepository personrepo){

        this.personrepo=personrepo;
    }

}
