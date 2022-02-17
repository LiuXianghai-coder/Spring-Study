package org.xhliu.springflowable.controller;

import org.flowable.task.api.Task;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.xhliu.springflowable.service.MineFlowService;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author xhliu
 * @create 2022-02-17-17:20
 **/
@RestController
public class MineFlowRestController {
    @Resource
    private MineFlowService flowService;

    @PostMapping(path = "/process")
    public void startProcessInstance() {
        flowService.startProcess();
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = flowService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : tasks)
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));

        return dtos;
    }

    static class TaskRepresentation {
        private String id;
        private String name;
        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
