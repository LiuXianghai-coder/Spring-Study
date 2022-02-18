package org.xhliu.springflowable.controller;

import lombok.*;
import org.flowable.task.api.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhliu.springflowable.service.FlowableService;
import org.xhliu.springflowable.service.impl.MineFlowService;

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
    @Resource(name = "mineService")
    private FlowableService flowService;

    @PostMapping(path = "/process", consumes = {"application/json"})
    public ResponseEntity<String> startProcessInstance(
            @RequestBody StartProcessRepresentation representation
    ) {
        flowService.startProcess(representation.getAssignee());

        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = flowService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<>();
        for (Task task : tasks)
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));

        return dtos;
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    static class TaskRepresentation {
        private String id;
        private String name;
    }

    static class StartProcessRepresentation {
        private String assignee;

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }
}
