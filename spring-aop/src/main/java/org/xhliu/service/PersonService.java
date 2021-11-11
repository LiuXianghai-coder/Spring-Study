package org.xhliu.service;

import org.xhliu.entity.Person;

public interface PersonService {
    Person createPerson(String firstName, String lastName, int age);

    Person queryPerson(String firstName);
}
