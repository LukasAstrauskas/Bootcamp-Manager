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


    @GetMapping("/student-homepage")
    public String showStudentTasks(Model model, Principal principal) {

        List<Student> allStudents = studentService.getAllStudents();
        for (Student student : allStudents)
            if (student.getEmail().equals(principal.getName())) {
                List<Task> tasksList = new ArrayList<>();
                if (student.getBootcamp() != null)
                    tasksList.addAll(student.getBootcamp().getTasks());
                model.addAttribute("thisStudent", student);
                model.addAttribute("tasksList", tasksList);
                model.addAttribute("helper", new StudentHelper(studentService));
                break;
            }
        return "student-homepage";
    }

    @GetMapping("/student-task/{id}/{studentId}")
    public String displayTaskPage(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "studentId") long studentId,
                                  Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("thisStudent", studentService.getStudentById(studentId));
        model.addAttribute("link", "resource link: ");
        model.addAttribute("helper", new StudentHelper(studentService));
        return "student-task";
    }

    @GetMapping("/set-task-completed/{id}/{studentId}")
    public String setTaskCompleted(@PathVariable(value = "id") long id,
                                   @PathVariable(value = "studentId") long studentId
    ) {
        Student thisStudent = studentService.getStudentById(studentId);
        thisStudent.setTaskCompleted(taskService.getTaskById(id));
        studentService.saveStudent(thisStudent);
        return "redirect:/student-homepage";
    }

    @GetMapping("/unset-task-completed/{id}/{studentId}")
    public String unsetTaskCompleted(@PathVariable(value = "id") long id,
                                     @PathVariable(value = "studentId") long studentId
    ) {
        Student thisStudent = studentService.getStudentById(studentId);
        thisStudent.unsetTaskCompleted(taskService.getTaskById(id));
        studentService.saveStudent(thisStudent);
        return "redirect:/student-homepage";
    }


}
