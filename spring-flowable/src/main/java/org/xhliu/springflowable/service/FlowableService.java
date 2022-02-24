package org.xhliu.springflowable.service;

import org.flowable.task.api.Task;

import java.util.List;

/**
 * @author xhliu
 * @create 2022-02-18-14:04
 **/
public interface FlowableService {
    void startProcess(String assignee);

    List<Task> getTasks(String assignee);

    void createDemoUsers();
}
