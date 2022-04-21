package com.bootcamp.bootcampmanager.user;

import com.bootcamp.bootcampmanager.admin.Admin;
import com.bootcamp.bootcampmanager.admin.AdminService;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.student.StudentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Setter
@Getter
public class UserContainer {

    private long id;
    private long roleId;
    private String role;
    private String homepage;


    public UserContainer(String email, AdminService adminService, LecturerService lecturerService, StudentService studentService) {

        for (Student i : studentService.getAllStudents())
            if (i.getEmail().equals(email)) {
                this.homepage = "/student-homepage";
                this.role = "student";
                this.roleId = 3;
                this.id = i.getId();
                return;
            }
        for (Admin i : adminService.getAllAdmins())
            if (i.getEmail().equals(email)) {
                this.homepage = "/bootcamps";
                this.role = "admin";
                this.roleId = 1;
                this.id = i.getId();
                return;
            }
        for (Lecturer i : lecturerService.getAllLecturers())
            if (i.getEmail().equals(email)) {
                this.homepage = "/students";
                this.role = "lecturer";
                this.roleId = 2;
                this.id = i.getId();
                return;
            }

    }

}
