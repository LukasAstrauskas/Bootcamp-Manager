package com.bootcamp.bootcampmanager.user;

import com.bootcamp.bootcampmanager.admin.Admin;
import com.bootcamp.bootcampmanager.admin.AdminService;
import com.bootcamp.bootcampmanager.config.encoder.Encoder;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.mail.Mail;
import com.bootcamp.bootcampmanager.mail.MailService;
import com.bootcamp.bootcampmanager.mail.MailThread;
import com.bootcamp.bootcampmanager.password.PasswordGeneratorService;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private LecturerService lecturerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        Counter counter = new Counter();
        model.addAttribute("adminsList", adminService.getAllAdmins());
        model.addAttribute("lecturersList", lecturerService.getAllLecturers());
        model.addAttribute("studentsList", studentService.getAllStudents());
        model.addAttribute("counter", counter);
        return "users";
    }

    @GetMapping("/new-user")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        if (user.getRoles() == null)
            return "redirect:/users";
        for (User i : userService.getAllUsers())
            if (i.getEmail().equals(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Student i : studentService.getAllStudents())
            if (i.getEmail().equals(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Admin i : adminService.getAllAdmins())
            if (i.getEmail().equals(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Lecturer i : lecturerService.getAllLecturers())
            if (i.getEmail().equals(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();

        if (user.getRoles().equals("ROLE_ADMIN")) {
            passwordGeneratorService.generateRandomPassword(user);
            Admin admin = new Admin(user.getFirstName(), user.getLastName(), user.getEmail());

//            Hashing password, Encoder gives customized passwordencoder
            String encodedPassword = Encoder.get().encode(admin.getPassword());
            admin.setPassword(encodedPassword);

            adminService.saveAdmin(admin);
        } else if (user.getRoles().equals("ROLE_STUDENT")) {
            passwordGeneratorService.generateRandomPassword(user);
            Student student = new Student(user.getFirstName(), user.getLastName(), user.getEmail());

            //            Hashing password, Encoder gives customized passwordencoder
            String encodedPassword = Encoder.get().encode(student.getPassword());
            student.setPassword(encodedPassword);

            studentService.saveStudent(student);
        } else if (user.getRoles().equals("ROLE_LECTURER")) {
            passwordGeneratorService.generateRandomPassword(user);
            Lecturer lecturer = new Lecturer(user.getFirstName(), user.getLastName(), user.getEmail());

            //           Hashing password, Encoder gives customized passwordencoder
            String encodedPassword = Encoder.get().encode(lecturer.getPassword());
            lecturer.setPassword(encodedPassword);

            lecturerService.saveLecturer(lecturer);
        }

        MailThread mailThread = new MailThread(mailService, user);
        mailThread.start();
        return "redirect:/users";
    }

    @GetMapping("/repeating-emails/{email}")
    public String repeatingEmail(Model model, @ModelAttribute("email") String email) {
        model.addAttribute("email", email);
        return "repeating-emails";
    }

    @GetMapping("/update-user/{id}")
    public String showUserFormForUpdate(@PathVariable(value = "id") long id,
                                        Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "update-user";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        this.userService.deleteUserById(id);
        return "redirect:/users";
    }
}