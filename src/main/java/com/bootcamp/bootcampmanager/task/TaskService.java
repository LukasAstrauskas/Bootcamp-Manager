package com.bootcamp.bootcampmanager.task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();
    void saveTask(Task task);
    Task getTaskById(Long id);
    void deleteTaskById(Long id);

}
