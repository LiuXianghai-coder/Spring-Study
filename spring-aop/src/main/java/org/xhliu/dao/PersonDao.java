package org.xhliu.dao;

import org.xhliu.entity.Person;

import java.util.List;

public interface PersonDao {
    Person findPersonByFirstName(String firstName);

    List<Person> findPersonsByAge(int age);
}
