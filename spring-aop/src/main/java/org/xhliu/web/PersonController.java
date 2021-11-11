package org.xhliu.web;

import org.xhliu.entity.Person;
import org.xhliu.service.PersonService;

import javax.annotation.Resource;

public class PersonController {
    @Resource
    private PersonService personService;

    public Person getPerson(String firstName) {
        return personService.queryPerson(firstName);
    }
}
