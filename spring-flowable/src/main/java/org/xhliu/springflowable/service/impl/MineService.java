package org.xhliu.springflowable.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhliu.springflowable.entity.Person;
import org.xhliu.springflowable.repository.PersonRepository;
import org.flowable.task.api.Task;
import org.xhliu.springflowable.service.FlowableService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xhliu
 * @create 2022-02-18-11:49
 **/
@Slf4j
@Transactional
@Service(value = "mineService")
public class MineService implements FlowableService {
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private PersonRepository personRepository;

    @Override
    public void startProcess(String assignee) {
        Optional<Person> optional = personRepository.findByUsername(assignee);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("指定的受理人不存在");
        }

        Person person = optional.get();
        Map<String, Object> variables = new HashMap<>();
        variables.put("person", person);
        runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
    }

    @Override
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Override
    public void createDemoUsers() {
        if (personRepository.findAll().size() == 0) {
            Person p1 = new Person("jbarrez", "Joram", "Barrez", LocalDateTime.now());
            Person p2 = new Person("trademakers", "Tijs", "Rademakers", LocalDateTime.now());

            personRepository.save(p1);
            log.info("save demo user {}", p1);

            personRepository.save(p2);
            log.info("save demo user {}", p2);
        }
    }
}
