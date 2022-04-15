package com.bootcamp.bootcampmanager.bootcamp;


import com.bootcamp.bootcampmanager.admin.Admin;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bootcamps")
@Getter
@Setter
@NoArgsConstructor
public class Bootcamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;

    @OneToMany(mappedBy = "bootcamp")
    private List<Student> students;

    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany(mappedBy = "joinedBootcamp")
    List<Lecturer> campLecturers;

    @ManyToMany(mappedBy = "managingBootcamp")
    List<Admin> adminList;
}
