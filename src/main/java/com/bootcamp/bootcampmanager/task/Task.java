package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.filedb.FileDB;
import com.bootcamp.bootcampmanager.student.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @Column
    private String linkURL;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileDB fileDB;

    @ManyToMany(mappedBy = "tasks")
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "id")
    private Bootcamp bootcamp;

    /* File and link mandatory */
    public Task(String name, String description, Date deadline, FileDB fileDB, String linkURL) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.fileDB = fileDB;
        this.linkURL = linkURL;
    }

    /* File mandatory */
    public Task(String name, String description, Date deadline, FileDB fileDB) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.fileDB = fileDB;
    }

    /* Link mandatory */
    public Task(String name, String description, Date deadline, String linkURL) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.linkURL = linkURL;
    }

    /* No File or Link mandatory */
    public Task(String name, String description, Date deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name) {
        this.name = name;
    }
}
