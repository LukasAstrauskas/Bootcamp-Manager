package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import com.bootcamp.bootcampmanager.filedb.FileDBService;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private FileDBService fileDBService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private BootcampService bootcampService;

    @GetMapping("/tasks")
    public String showAllTasks(Model model) {
        model.addAttribute("tasksList", taskService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/new-task")
    public String showNewTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("id", new Bootcamp());
        model.addAttribute("bootcamps", bootcampService.getAllBootcamps());
        return "new-task";
    }

    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute("bootcamp") Bootcamp bootcamp,
                           @ModelAttribute("task") Task task,
                           @RequestParam("file") MultipartFile[] files) {
        task.setFileDB(fileDBService.saveFile(files[0], task));
        if (bootcamp.getId() != 0) {
            try {
                Bootcamp camp = bootcampService.getBootcampById(bootcamp.getId());
                task.setBootcamp(camp);

            } catch (Exception e) {
                System.out.println("\n\n\n\n Whoops!? Something went wrong!!!" + e.getMessage() + "\n\n\n\n");
            }
        }
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/update-task/{id}")
    public String showTaskFormForUpdate(@PathVariable(value = "id") long id,
                                        Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "update-task";
    }

    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") long id) {
        this.taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/task/{id}")
    public String displayTaskPage(@PathVariable(value = "id") long id,
                                  Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "task";
    }

    @GetMapping("/task-status/{id}")
    public String showTaskCheckbox(@PathVariable(value = "id") long id,
                                   Model model) {
        model.addAttribute("taskStatus", taskService.getTaskById(id));
        return "task";
    }

    @GetMapping("/lecturer-tasks/{id}")
    public String showLecturerTasks(@PathVariable(value = "id") long id,
                                    Model model) {
        model.addAttribute("tasksList", lecturerService.getLecturersTasks(id));
        model.addAttribute("thisLecturer", lecturerService.getLecturerById(id));
        return "lecturer-tasks";
    }
}
