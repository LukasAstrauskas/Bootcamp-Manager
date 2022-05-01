package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.studentTaskStatus.StudentTaskStatus;

import com.bootcamp.bootcampmanager.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends User {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "id")
    private Bootcamp bootcamp;

    //    ManyToMany mapping using additional table "StudentTaskStatus"
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    List<StudentTaskStatus> taskStatusSet;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email);
        setPassword(password);
        setRoles("ROLE_STUDENT");
    }
}
