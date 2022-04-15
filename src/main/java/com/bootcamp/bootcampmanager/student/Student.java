package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.task.Task;

import com.bootcamp.bootcampmanager.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends User {

    @Column
    private String completedTasks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "id")
    private Bootcamp bootcamp;

    @ManyToMany
    @JoinTable(
            name = "student_tasks",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    List<Task> tasks;

    private String trainerName;

    public Student(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        setRoles("ROLE_STUDENT");
    }

    public Student() {
    }

    public void setTaskCompleted(Task task){

        for(String i : this.completedTasks.split(","))
            if(task.getId().toString().equals(i))
                return;
        this.completedTasks += "," + task.getId();
    }

    public void unsetTaskCompleted(Task task){
        String[] completed = this.completedTasks.split(",");
        this.completedTasks = "0";
        for(String i : completed)
            if(!task.getId().toString().equals(i))
                this.completedTasks += "," + i;
    }

}
