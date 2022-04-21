package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.task.Task;
import com.bootcamp.bootcampmanager.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentHomepageController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TaskService taskService;

    @Autowired
    StatusRepository statusRepository;


    @GetMapping("/student-homepage")
    public String showStudentTasks(Model model, Principal principal) {
//        Getting studend by using principal and Repository method "findStudentByEmail"
        Student student = studentService.getStudentByEmail(principal.getName());

        model.addAttribute("student", student);
        return "student-homepage";
    }

    @GetMapping("/student-task/{taskId}")
    public String displayTaskPage(@PathVariable("taskId") long taskId,
                                  Model model) {
        Task task = taskService.getTaskById(taskId);

        model.addAttribute("task", task);
        return "student-task";
    }

    @GetMapping("/changeStatus/{id}")
    public String changeStudentTaskStatus(@PathVariable("id") long studentTaskId) {
        StudentTaskStatus byId = statusRepository.getById(studentTaskId);
        Boolean status = byId.getStatus();
        byId.setStatus(!status);
        statusRepository.save(byId);
        return "redirect:/student-homepage";
    }
}
