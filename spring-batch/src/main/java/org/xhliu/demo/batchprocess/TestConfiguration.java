package org.xhliu.demo.batchprocess;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableBatchProcessing
public class TestConfiguration {
    private final JobBuilderFactory jobBuilderFactory;

    @Resource(name = "step1")
    private Step step1;

    @Resource(name = "step2")
    private Step step2;

    public TestConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean(name = "importUserJob")
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .next(step2)
                .build();
    }
}
