package com.bootcamp.bootcampmanager.bootcamp;


import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.student.StudentService;
import com.bootcamp.bootcampmanager.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class BootcampController {

    private final BootcampService bootcampService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final TaskService taskService;

    @Autowired
    public BootcampController(BootcampService bootcampService, StudentService studentService,
                              LecturerService lecturerService, TaskService taskService) {
        this.bootcampService = bootcampService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.taskService = taskService;
    }

    @GetMapping("/bootcamps")
    public String showAllBootcamps(Model model) {
        model.addAttribute("listBootcamps", bootcampService.getAllBootcamps());
        return "bootcamps";
    }

    @GetMapping("/new-bootcamp")
    public String showNewBootcampForm(Model model) {
        model.addAttribute("bootcamp", new Bootcamp());
        return "new-bootcamp";
    }

    @PostMapping("/save-bootcamp")
    public String saveBootcamp(@ModelAttribute("bootcamp") Bootcamp bootcamp) {
        bootcampService.saveBootcamp(bootcamp);
        return "redirect:/bootcamps";
    }

    @GetMapping("/update-bootcamp/{id}")
    public String showBootcampFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("bootcamp",
                bootcampService.getBootcampById(id));
        return "update-bootcamp";
    }

    @GetMapping("/delete-bootcamp/{id}")
    public String deleteBootcamp(@PathVariable("id") long id) {
        bootcampService.deleteBootcampById(id);
        return "redirect:/bootcamps";
    }

    @GetMapping("/bootcamp/{id}")
    public String showBootcampLecturers(@PathVariable("id") long campID, Model model) {
        model.addAttribute("bootcamp", bootcampService.getBootcampById(campID));
        model.addAttribute("freeTasks", taskService.getTasksWithNoBootCamp());
        model.addAttribute("freeStudents", studentService.getStudentsWithNoBootcamp());
        model.addAttribute("lecturers", lecturerService.getLecturersNotInCamp(campID));
        return "bootcamp";
    }

    @GetMapping("/link-students-form/{campID}")
    public String linkStudentsForm(@PathVariable long campID, Model model) {
        model.addAttribute("students", studentService.getStudentsWithNoBootcamp());
        model.addAttribute("bootcamp", bootcampService.getBootcampById(campID));
        return "link-student";
    }

    @GetMapping("/link-student/{campID}/{studID}")
    public String linkStudent(@PathVariable long campID, @PathVariable long studID) {
        studentService.linkStudentToBootcamp(campID, studID);
        return "redirect:/link-students-form/" + campID;
    }

    @GetMapping("/unlink-student/{id}")
    public String unlinkStudent(@PathVariable("id") long studID,
                                Model model) {
        Long campID = studentService.unlinkStudent(studID);
        return "redirect:/bootcamp/" + campID;
    }

    @GetMapping("/enrolled-student/{id}")
    public String enrolledStudent(@PathVariable("id") long studID,
                                  Model model) {
        model.addAttribute("student", studentService.getStudentById(studID));
        model.addAttribute("bootcamp", bootcampService.getAllBootcamps());
        return "enrolled-student";
    }

    @GetMapping("/link-lecturers-form/{campID}")
    public String linkLecturersForm(@PathVariable long campID, Model model) {
        model.addAttribute("lecturers", lecturerService.getLecturersNotInCamp(campID));
        model.addAttribute("bootcamp", bootcampService.getBootcampById(campID));
        return "link-lecturer";
    }

    @GetMapping("/link-lecturer/{campID}/{lecID}")
    public String linkLecturer(@PathVariable long campID, @PathVariable long lecID) {
        lecturerService.linkLecturerToBootcamp(campID, lecID);
        return "redirect:/link-lecturers-form/" + campID;
    }

    @GetMapping("/unlink-lecturer/{lecID}/{campID}")
    public String unlinkLecturer(@PathVariable long lecID,
                                 @PathVariable long campID) {
        lecturerService.unlinkLecFromCamp(lecID, campID);
        return "redirect:/bootcamp/" + campID;
    }

    // lecturer info, doubles lecturer/id page
    @GetMapping("/enrolled-lecturer/{lecID}/{campID}")
    public String enrolledLecturer(@PathVariable long lecID,
                                   @PathVariable long campID,
                                   Model model) {
        model.addAttribute("lecturer", lecturerService.getLecturerById(lecID));
        model.addAttribute("bootcamp", bootcampService.getBootcampById(campID));
        return "enrolled-lecturer";
    }

//    enrolled-task is in taskController as /task/{id}

    @GetMapping("/link-tasks-form/{campID}")
    public String linkTasksForm(@PathVariable long campID, Model model) {
        model.addAttribute("tasks", taskService.getTasksWithNoBootCamp());
        model.addAttribute("bootcamp", bootcampService.getBootcampById(campID));
        return "link-task";
    }

    @GetMapping("/link-task/{campID}/{taskID}")
    public String linkTask(@PathVariable long campID, @PathVariable long taskID) {
        taskService.linkTaskToBootcamp(campID, taskID);
        return "redirect:/link-tasks-form/" + campID;
    }

    @GetMapping("/unlink-task/{taskID}")
    public String unlinkTask(@PathVariable long taskID) {
        Long campID = taskService.unlinkTask(taskID);
        return "redirect:/bootcamp/" + campID;
    }
}
