package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.studentTaskStatus.StatusService;
import com.bootcamp.bootcampmanager.task.Task;
import com.bootcamp.bootcampmanager.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class StudentHomepageController {

    private final StudentService studentService;

    private final TaskService taskService;

    private final StatusService statusService;

    @Autowired
    public StudentHomepageController(StudentService studentService,
                                     TaskService taskService,
                                     StatusService statusService) {
        this.studentService = studentService;
        this.taskService = taskService;
        this.statusService = statusService;
    }


    @GetMapping("/student-homepage")
    public String showStudentTasks(Model model, Principal principal) {
//        Getting student by using principal and Repository method "findStudentByEmail"
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
        statusService.changeStatusByID(studentTaskId);
        return "redirect:/student-homepage";
    }
}
