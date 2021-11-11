package org.xhliu.service.impl;

import org.xhliu.entity.Person;
import org.xhliu.service.PersonService;

public class PersonServiceImpl implements PersonService {
    static Person person = null;

    public Person createPerson(String firstName, String lastName, int age) {
        person = new Person(firstName, lastName, age);
        return person;
    }

    public Person queryPerson(String firstName) {
        return person;
    }
}
