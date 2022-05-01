package com.bootcamp.bootcampmanager.task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<Task> getAllTasks();

    void saveTask(Task task);

    Task getTaskById(Long id);

    void deleteTaskById(Long id);

    List<Task> getTasksWithNoBootCamp();

    void linkTaskToBootcamp(long campID, long taskID);

    Long unlinkTask(long taskID);
}
