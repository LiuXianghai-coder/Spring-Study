package org.xhliu.demo.batchprocess;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.xhliu.demo.entity.Person;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
//@EnableBatchProcessing
public class BatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;

    @Resource(name = "step1")
    private Step step1;

    @Resource(name = "step2")
    private Step step2;

    @Autowired
    public BatchConfiguration(
            JobBuilderFactory jobBuilderFactory
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean(name = "reader")
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited().delimiter(",")
                .names("firstName", "lastName")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .build();
    }

    @Bean(name = "writer")
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(
                        new BeanPropertyItemSqlParameterSourceProvider<Person>()
                )
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }

    /*@Bean(name = "importUserJob")
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .next(step2)
                .build();
    }*/

    @Bean(name = "personItemProcessor")
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean(name = "personSecondStepProcess")
    public PersonSecondStepProcess secondStepProcess() {
        return new PersonSecondStepProcess();
    }
/*
    @Bean(name = "step1")
    public Step step1(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory
                .get("step1")
                .<Person, Person>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }


    @Bean(name = "step2")
    public Step step2(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory
                .get("step2")
                .<Person, Person>chunk(10)
                .reader(reader())
                .processor(secondStepProcess())
                .writer(writer)
                .build();
    }
    */
}
