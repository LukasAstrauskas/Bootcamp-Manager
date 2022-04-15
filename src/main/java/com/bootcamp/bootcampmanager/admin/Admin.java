package com.bootcamp.bootcampmanager.admin;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    @ManyToMany
    @JoinTable(
            name = "bootcamp_admins",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "bootcamp_id"))
    List<Bootcamp> managingBootcamp;

    public Admin(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        setRoles("ROLE_ADMIN");
    }
}
