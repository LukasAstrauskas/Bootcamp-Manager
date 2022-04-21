package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.task.Task;

import com.bootcamp.bootcampmanager.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends User {
    

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "id")
    private Bootcamp bootcamp;

    //    ManyToMany mapping using additional table "StudentTaskStatus"
    @OneToMany(mappedBy = "student")
    Set<StudentTaskStatus> taskStatusSet;

//    private String trainerName;

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        setRoles("ROLE_STUDENT");
    }

    public Student() {
    }



}
