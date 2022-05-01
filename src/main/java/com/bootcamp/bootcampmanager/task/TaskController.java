package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import com.bootcamp.bootcampmanager.filedb.FileDBService;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class TaskController {

    private final TaskService taskService;

    private final FileDBService fileDBService;

    private final LecturerService lecturerService;

    private final BootcampService bootcampService;

    @Autowired
    public TaskController(TaskService taskService, FileDBService fileDBService,
                          LecturerService lecturerService, BootcampService bootcampService) {
        this.taskService = taskService;
        this.fileDBService = fileDBService;
        this.lecturerService = lecturerService;
        this.bootcampService = bootcampService;
    }

    //  admin page
    @GetMapping("/tasks")
    public String showAllTasks(Model model) {
        model.addAttribute("tasksList", taskService.getAllTasks());
        return "tasks";
    }

    //    amin page
    @GetMapping("/new-task")
    public String showNewTaskForm(Model model) {
        model.addAttribute("bootcamps", bootcampService.getAllBootcamps());
        return "new-task";
    }

    //  admin page
    @PostMapping("/save-task")
    public String saveTask(@ModelAttribute("task") Task task,
                           @RequestParam("file") MultipartFile[] files) {
        task.setFileDB(fileDBService.saveFile(files[0], task));
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    //    not used in admin page
    @GetMapping("/update-task/{id}")
    public String showTaskFormForUpdate(@PathVariable(value = "id") long id,
                                        Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "update-task";
    }

    //  admin page
    @GetMapping("/delete-task/{id}")
    public String deleteTask(@PathVariable(value = "id") long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

    //  admin page
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

    // lecturer page, tasks in lect's camps
    @GetMapping("/lecturer-tasks")
    public String showLecturerTasks(Model model, Principal principal) {
        Lecturer lec = lecturerService.getLecturerByEmail(principal.getName());
        model.addAttribute("lecCamps", lec.getJoinedBootcamp());
        model.addAttribute("idDao", new IdDAO());
        return "lecturer-tasks";
    }

    // lecturer page, show students with task status
    @PostMapping("/task-students-status")
    public String showTaskWithStudentsStatus(@ModelAttribute("idDao") IdDAO idDao, Model model,
                                             Authentication auth) {
        if (idDao.getId() == 0) {
            return "redirect:/lecturer-tasks";
        }
        model.addAttribute("task", taskService.getTaskById(idDao.getId()));
        return "task-students-status";
    }

    //    in lecturer page add task form
    @GetMapping("/new-lecturer-task")
    public String showNewLecturerTaskForm(Model model, Principal principal) {
        model.addAttribute("task", new Task());
        model.addAttribute("bootcamps",
                lecturerService.getLecturersBootcampsByEmail(principal.getName()));
        return "new-lecturer-task";
    }

    //    used in lecturer page to add task
    @PostMapping("/save-lecturer-task")
    public String saveLecturerTask(@ModelAttribute("task") Task task,
                                   @RequestParam("file") MultipartFile[] files) {
        task.setFileDB(fileDBService.saveFile(files[0], task));
        taskService.saveTask(task);
        return "redirect:/lecturer-tasks";
    }

    // lecturer page, delete task
    @GetMapping("/delete-lecturer-task/{id}")
    public String deleteLecturerTask(@PathVariable("id") long id) {
        System.out.println("Controller Deleting task: " + taskService.getTaskById(id).getName());
        taskService.deleteTaskById(id);
        return "redirect:/lecturer-tasks";
    }

    /*lecturer page. Dont update fileDB */
    @PostMapping("/update-lecturer-task")
    public String updateLecturerTask(@ModelAttribute("task") Task task) {
        taskService.saveTask(task);
        return "redirect:/lecturer-tasks";
    }
}
