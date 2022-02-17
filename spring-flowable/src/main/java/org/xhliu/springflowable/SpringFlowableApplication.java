package org.xhliu.springflowable;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringFlowableApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringFlowableApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringFlowableApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(
            RepositoryService repoService,
            RuntimeService runtimeService,
            TaskService taskService
    ) {
        return args -> {
            log.info("Number of process definitions : "
                    + repoService.createProcessDefinitionQuery().count());
            log.info("Number of tasks : " + taskService.createTaskQuery().count());
            runtimeService.startProcessInstanceByKey("oneTaskProcess");
            log.info("Number of tasks after process start: "
                    + taskService.createTaskQuery().count());
        };
    }

}
