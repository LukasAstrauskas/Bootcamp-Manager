package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
public class Lecturer extends User {

    @Column
    private boolean isTrainer;

    @ManyToMany
    @JoinTable(
            name = "bootcamp_lecturers",
            joinColumns = @JoinColumn(name = "lecturer_id"),
            inverseJoinColumns = @JoinColumn(name = "bootcamp_id"))
    List<Bootcamp> joinedBootcamp;

    public Lecturer(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        setRoles("ROLE_LECTURER");
    }

}
