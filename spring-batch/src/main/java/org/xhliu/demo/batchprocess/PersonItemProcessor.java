package org.xhliu.demo.batchprocess;

import org.springframework.batch.item.ItemProcessor;
import org.xhliu.demo.entity.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        return new Person(firstName, lastName);
    }
}
