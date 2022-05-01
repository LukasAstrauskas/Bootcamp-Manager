package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {


    private final TaskRepository taskRepository;
    private final BootcampService bootcampService;

    @Autowired
    public TaskServiceImp(TaskRepository taskRepository, BootcampService bootcampService) {
        this.taskRepository = taskRepository;
        this.bootcampService = bootcampService;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(Task task) {
//        Bootcamp bootcamp = task.getBootcamp();
//        for (Student student : bootcamp.getStudents()) {
//          TODO add task to each student in bootcamp
//        }
        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        Optional<Task> optional = taskRepository.findById(id);
        Task task;
        if (optional.isPresent()) {
            task = optional.get();
        } else {
            throw new RuntimeException("Not found task: " + id);
        }
        return task;
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksWithNoBootCamp() {
        return taskRepository.findAll().stream()
                .filter(task -> task.getBootcamp() == null)
                .collect(Collectors.toList());
    }

    @Override
    public void linkTaskToBootcamp(long campID, long taskID) {
        Bootcamp camp = bootcampService.getBootcampById(campID);
        Task task = getTaskById(taskID);
        task.setBootcamp(camp);
        taskRepository.save(task);
    }

    @Override
    public Long unlinkTask(long taskID) {
        Task task = getTaskById(taskID);
        long campID = task.getBootcamp().getId();
        task.setBootcamp(null);
        taskRepository.save(task);
        return campID;
    }
}
