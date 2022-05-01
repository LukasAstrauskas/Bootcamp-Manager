package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class StudentController {


    private final StudentService studentService;
    private final LecturerService lecturerService;


    @Autowired
    public StudentController(StudentService studentService,
                             LecturerService lecturerService) {
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @GetMapping("/students")
    public String showAllStudents(Model model, Principal principal) {
        Lecturer lec = lecturerService.getLecturerByEmail(principal.getName());
        List<Student> students = lecturerService.getAllLecturersStudents(lec);
        model.addAttribute("bootcampsList", lec.getJoinedBootcamp());
        model.addAttribute("studentsList", students);
        return "students";
    }


    @GetMapping("/new-student")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new-student";
    }

    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }


    @GetMapping("/update-student/{id}")
    public String updateStudentForm(@PathVariable("id") long id,
                                    Model model, Authentication auth) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("bootcamps", lecturerService
                .getLecturersBootcampsByEmail(auth.getName()));
        return "update-student";
    }

    @PostMapping("/update-student")
    public String updateStudentInfo(@ModelAttribute("student") Student student) {
        System.out.println("Updated: " + student.getFirstName());
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable("id") long id) {
        this.studentService.deleteStudentById(id);
        return "redirect:/students";
    }

    @GetMapping("/info-student/{id}")
    public String showStudentInfo(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "student";
    }
}
