package org.xhliu.demo.batchprocess;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.xhliu.demo.entity.Person;

import javax.annotation.Resource;

@Component
public class StepComponent {
    private final StepBuilderFactory stepBuilderFactory;

    @Resource(name = "reader")
    ItemReader<Person> reader;

    @Resource(name = "writer")
    JdbcBatchItemWriter<Person> writer;

    @Resource(name = "personItemProcessor")
    PersonItemProcessor processor;

    @Resource(name = "personSecondStepProcess")
    PersonSecondStepProcess secondStepProcess;

    @Autowired
    public StepComponent(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean(name = "step1")
    public Step step1() {
        return stepBuilderFactory
                .get("step1")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean(name = "step2")
    public Step step2() {
        return stepBuilderFactory
                .get("step2")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(secondStepProcess)
                .writer(writer)
                .build();
    }
}
