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

import java.security.Principal;
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
        model.addAttribute("adminsList", adminService.getAllAdmins());
        model.addAttribute("lecturersList", lecturerService.getAllLecturers());
        model.addAttribute("studentsList", studentService.getAllStudents());
        return "users";
    }

    @GetMapping("/new-user")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        user.setEnabled(true);
        user.setEmail(user.getEmail().toLowerCase());
        if (user.getRoles() == null)
            return "redirect:/users";
        for (User i : userService.getAllUsers())
            if (i.getEmail().equalsIgnoreCase(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Student i : studentService.getAllStudents())
            if (i.getEmail().equalsIgnoreCase(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Admin i : adminService.getAllAdmins())
            if (i.getEmail().equalsIgnoreCase(user.getEmail()))
                return "redirect:/repeating-emails/" + user.getEmail();
        for (Lecturer i : lecturerService.getAllLecturers())
            if (i.getEmail().equalsIgnoreCase(user.getEmail()))
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
    public String showUserFormForUpdate(@PathVariable("id") long id,
                                        Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "update-user";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        this.userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/user/{id}/{category}")
    public String showThisUser(@PathVariable("id") long id, @PathVariable("category") long cat, Model model) {
        if (cat == 1) {
            model.addAttribute("user", adminService.getAdminById(id));
            model.addAttribute("role", "admin");
        } else if (cat == 2) {
            model.addAttribute("user", lecturerService.getLecturerById(id));
            model.addAttribute("role", "lecturer");
        } else if (cat == 3) {
            model.addAttribute("user", studentService.getStudentById(id));
            model.addAttribute("role", "student");
        } else
            return "users";
        return "user";
    }

    @GetMapping("/change-password/{id}/{role}")
    public String showNewPasswordForm(@PathVariable("id") long id, @PathVariable("role") String role, Model model) {
        String newPassword = "enter new password";
        DataContainer dataContainer = new DataContainer(newPassword, id, role);
        model.addAttribute("dataContainer", dataContainer);
        model.addAttribute("id", id);
        if (role.equals("admin")) {
            model.addAttribute("role", 1);
        } else if (role.equals("lecturer")) {
            model.addAttribute("role", 2);
        } else {
            model.addAttribute("role", 3);
        }
        return "change-password";
    }

    @PostMapping("/save-password")
    public String savePassword(@ModelAttribute("dataContainer") DataContainer dataContainer) {

        if (dataContainer.getRole().equals("admin")) {
            Admin admin = adminService.getAdminById(dataContainer.getId());
            admin.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            adminService.saveAdmin(admin);
        } else if (dataContainer.getRole().equals("lecturer")) {
            Lecturer lecturer = lecturerService.getLecturerById(dataContainer.getId());
            lecturer.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            lecturerService.saveLecturer(lecturer);
        } else if (dataContainer.getRole().equals("student")) {
            Student student = studentService.getStudentById(dataContainer.getId());
            student.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            studentService.saveStudent(student);
        }
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        UserContainer userContainer = new UserContainer(principal.getName(), adminService, lecturerService, studentService);
        model.addAttribute("userContainer", userContainer);
        model.addAttribute("role", userContainer.getRole());
        if (userContainer.getRoleId() == 3) {
            model.addAttribute("user", studentService.getStudentById(userContainer.getId()));
        }
        if (userContainer.getRoleId() == 2) {
            model.addAttribute("user", lecturerService.getLecturerById(userContainer.getId()));
        }
        if (userContainer.getRoleId() == 1) {
            model.addAttribute("user", adminService.getAdminById(userContainer.getId()));
        }
        return "profile";
    }

    @GetMapping("/change-profile-password/{id}/{role}")
    public String showNewProfilePasswordForm(@PathVariable("id") long id, @PathVariable("role") String role, Model model) {
        String newPassword = "enter new password";
        DataContainer dataContainer = new DataContainer(newPassword, id, role);
        model.addAttribute("dataContainer", dataContainer);
        model.addAttribute("id", id);
        if (role.equals("admin")) {
            model.addAttribute("role", 1);
        } else if (role.equals("lecturer")) {
            model.addAttribute("role", 2);
        } else {
            model.addAttribute("role", 3);
        }
        return "change-profile-password";
    }

    @PostMapping("/save-profile-password")
    public String saveProfilePassword(@ModelAttribute("dataContainer") DataContainer dataContainer) {

        if (dataContainer.getRole().equals("admin")) {
            Admin admin = adminService.getAdminById(dataContainer.getId());
            admin.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            adminService.saveAdmin(admin);
        } else if (dataContainer.getRole().equals("lecturer")) {
            Lecturer lecturer = lecturerService.getLecturerById(dataContainer.getId());
            lecturer.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            lecturerService.saveLecturer(lecturer);
        } else if (dataContainer.getRole().equals("student")) {
            Student student = studentService.getStudentById(dataContainer.getId());
            student.setPassword(Encoder.get().encode(dataContainer.getNewPassword()));
            studentService.saveStudent(student);
        }
        return "redirect:/profile";
    }
}