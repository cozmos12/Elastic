package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductServiceTasks{
    @Autowired
    TaskService taskService;


    public void getTasks() {
        List<Task> tasks = taskService.createTaskQuery().active().list();

        /*
        for (Task task : tasks) {
            final Map<String, Object> vars = taskService.getVariables(task.getId());

            taskService.complete(task.getId(), vars);
            for (Map.Entry<String, Object> asdf : vars.entrySet()) {
                log.info("key={}, value={}", asdf.getKey(), asdf.getValue());
            }
        }*/
    }


}
