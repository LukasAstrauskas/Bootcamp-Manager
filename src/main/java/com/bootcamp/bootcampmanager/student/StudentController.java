package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.task.FilterContainer;
import com.bootcamp.bootcampmanager.task.Task;
import com.bootcamp.bootcampmanager.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class StudentController {


    private StudentService studentService;
    private BootcampService bootcampService;
    private TaskService taskService;
    private LecturerService lecturerService;


    @Autowired
    public StudentController(StudentService studentService,
                             BootcampService bootcampService,
                             TaskService taskService,
                             LecturerService lecturerService) {
        this.studentService = studentService;
        this.bootcampService = bootcampService;
        this.taskService = taskService;
        this.lecturerService = lecturerService;
    }

    @GetMapping("/students")
    public String showAllStudents(Model model, Principal principal) {

        FilterContainer filterContainer = new FilterContainer( -1);
        filterContainer.unsetShow();
        List<Lecturer> allLecturers = lecturerService.getAllLecturers();
        for(Lecturer lecturer : allLecturers)
            if(lecturer.getEmail().equals(principal.getName())){
                List<Student> studentsList = new ArrayList<>();
                for(Bootcamp bootcamp : lecturer.getJoinedBootcamp())
                        studentsList.addAll(bootcamp.getStudents());
                model.addAttribute("bootcampsList", lecturer.getJoinedBootcamp());
                model.addAttribute("studentsList", studentsList);
                model.addAttribute("thisLecturer", lecturer);
                return "students";
            }

        model.addAttribute("bootcampsList", bootcampService.getAllBootcamps());
        model.addAttribute("studentsList", studentService.getAllStudents());
        model.addAttribute("thisLecturer", lecturerService.getAllLecturers().get(0));
        return "students";
    }


    @GetMapping("/new-student")
    public String showNewStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "new-student";
    }

    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        student.setEnabled(true);
        student.setRoles("ROLE_STUDENT");
        try{
            long bootcampID = student.getBootcamp().getId();
            if(bootcampID != 0)
                student.setBootcamp(bootcampService.getBootcampById(bootcampID));
        }
        catch (Exception e){
            studentService.saveStudent(student);
            return "redirect:/students";
        }
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/update-student/{id}")
    public String showStudentFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("bootcamps", bootcampService.getAllBootcamps());
        return "update-student";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        this.studentService.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("/info-student/{id}")
    public String showStudentInfo(@PathVariable(value = "id") long id, Model model) {

        model.addAttribute("student", studentService.getStudentById(id));
//        model.addAttribute("helper", new StudentHelper(studentService));
        return "student";
    }
}
