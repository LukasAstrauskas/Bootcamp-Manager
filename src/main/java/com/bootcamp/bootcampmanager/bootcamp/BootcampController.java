package com.bootcamp.bootcampmanager.bootcamp;

import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
public class BootcampController {

    private final BootcampService bootcampService;
    private final StudentService studentService;
    private final LecturerService lecturerService;

    @Autowired
    public BootcampController(BootcampService bootcampService, StudentService studentService, LecturerService lecturerService) {
        this.bootcampService = bootcampService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }

    @GetMapping("/bootcamps")
    public String showAllBootcamps(Model model) {
        model.addAttribute("listBootcamps", bootcampService.getAllBootcamps());
        /*return "bootcamp/allBootcamp";*/
        return "bootcamps";
    }

    @GetMapping("/new-bootcamp")
    public String showNewBootcampForm(Model model) {
        model.addAttribute("bootcamp", new Bootcamp());
        /*return "bootcamp/new-bootcamp";*/
        return "new-bootcamp";
    }

    @PostMapping("/save-bootcamp")
    public String saveBootcamp(@ModelAttribute("bootcamp") Bootcamp bootcamp) {
        bootcampService.saveBootcamp(bootcamp);
        return "redirect:/bootcamps";
    }

    @GetMapping("/update-bootcamp/{id}")
    public String showBootcampFormForUpdate(@PathVariable( value = "id") long id, Model model) {
        Bootcamp bootcamp = bootcampService.getBootcampById(id);
        model.addAttribute("bootcamp", bootcamp);
        return "update-bootcamp";
    }

    @GetMapping("/delete-bootcamp/{id}")
    public String deleteBootcamp(@PathVariable (value = "id") long id) {
        this.bootcampService.deleteBootcampById(id);
        return "redirect:/bootcamps";
    }

    @GetMapping("/bootcamp/{id}")
    public String showBootcamp(@PathVariable (value = "id") long id, Model model) {
        model.addAttribute("bootcamp",  bootcampService.getBootcampById(id));
        return "bootcamp";
    }

    @GetMapping(value = "/link-student/{id}")
    public String showStudentCheckbox(@PathVariable (value = "id") long id, Model model) {
        model.addAttribute("students",  studentService.getAllStudents());
        model.addAttribute("bootcamp",  bootcampService.getBootcampById(id));
        return "link-student";
    }

    @PostMapping("/insert/{id}")
    public String insertStudent(@ModelAttribute("bootcamp") Bootcamp bootcamp, @PathVariable (value = "id") long id) {
        Bootcamp thisBootcamp = bootcampService.getBootcampById(id);
        for(Student student : bootcamp.getStudents()){
            student.setBootcamp(thisBootcamp);
            studentService.saveStudent(student);
        }
        return "redirect:/bootcamp/" + id;
    }

    @GetMapping(value = "/unlink-student/{id}")
    public String unlinkStudent(@PathVariable (value = "id") long id, Model model) {

        Student student = studentService.getStudentById(id);
        long index = student.getBootcamp().getId();
        student.setBootcamp(null);
        studentService.saveStudent(student);
        return new String("redirect:/bootcamp/" + index);
    }

    @GetMapping(value = "/enrolled-student/{id}")
    public String enrolledStudent(@PathVariable (value = "id") long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        model.addAttribute("bootcamp", bootcampService.getAllBootcamps());
        return "enrolled-student";
    }

    @GetMapping(value = "/link-lecturer/{id}")
    public String showLecturerCheckbox(@PathVariable (value = "id") long id, Model model) {
        model.addAttribute("lecturers",  lecturerService.getAllLecturers());
        model.addAttribute("bootcamp",  bootcampService.getBootcampById(id));
        return "link-lecturer";
    }

    @PostMapping("/insertLecturer/{id}")
    public String insertExample(@ModelAttribute("bootcamp") Bootcamp bootcamp, @PathVariable (value = "id") long id) {
        Bootcamp thisBootcamp = bootcampService.getBootcampById(id);
        for(Lecturer lecturer : bootcamp.getCampLecturers()){
            List<Bootcamp> joinedBootcamps = lecturer.getJoinedBootcamp();
            if(!joinedBootcamps.contains(thisBootcamp)){
                joinedBootcamps.add(thisBootcamp);
                lecturer.setJoinedBootcamp(joinedBootcamps);
                lecturerService.saveLecturer(lecturer);
            }

            List<Lecturer> addedLecturers = thisBootcamp.getCampLecturers();
            if(!addedLecturers.contains(thisBootcamp)){
                addedLecturers.add(lecturer);
                thisBootcamp.setCampLecturers(addedLecturers);
                bootcampService.saveBootcamp(thisBootcamp);
            }
        }
        return "redirect:/bootcamp/" + id;
    }

    @GetMapping(value = "/unlink-lecturer/{id}/{ip}")
    public String unlinkLecturer(@PathVariable (value = "id") long id, @PathVariable (value = "ip") long ip, Model model) {

        Lecturer lecturer = lecturerService.getLecturerById(id);
        List<Bootcamp> bootcamps = lecturer.getJoinedBootcamp();
        bootcamps.remove(bootcampService.getBootcampById(ip));
        lecturer.setJoinedBootcamp(bootcamps);
        lecturerService.saveLecturer(lecturer);
        return new String("redirect:/bootcamp/" + ip);
    }


    @GetMapping(value = "/enrolled-lecturer/{id}/{ip}")
    public String enrolledStudent(@PathVariable (value = "id") long id, @PathVariable (value = "ip") long ip, Model model) {
        model.addAttribute("lecturer", lecturerService.getLecturerById(id));
        model.addAttribute("bootcamp", bootcampService.getBootcampById(ip));
        return "enrolled-lecturer";
    }
}
