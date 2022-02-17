package org.xhliu.springflowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xhliu
 * @create 2022-02-17-17:20
 **/
@Service
public class MineFlowService {
    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Transactional
    public void startProcess() {
        runtimeService.startProcessInstanceByKey("oneTaskProcess");
    }

    @Transactional
    public List<Task> getTasks(String assigned) {
        return taskService.createTaskQuery().taskAssignee(assigned).list();
    }
}
