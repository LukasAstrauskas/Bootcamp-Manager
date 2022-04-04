package com.bootcamp.bootcampmanager.Task;

import com.bootcamp.bootcampmanager.Course.Course;
import com.bootcamp.bootcampmanager.Link.Link;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Tasks")
@Data  // added lombok so dont have to make getters and setters
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    Date dateFrom;
    Date dateTo;
    boolean isCompleted;
    @ManyToOne
    @JoinColumn(name="course_id",nullable = false)
    private Course course;

    @OneToMany(targetEntity = Link.class, mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Link> links;

}
