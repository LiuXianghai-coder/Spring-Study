package org.xhliu.demo.batchprocess;

import org.springframework.batch.item.ItemProcessor;
import org.xhliu.demo.entity.Person;

public class PersonSecondStepProcess implements ItemProcessor<Person, Person> {
    @Override
    public Person process(Person person) throws Exception {
        return new Person(person.getFirstName() + "-Step2", person.getLastName() + "-Step2");
    }
}
