package com.bootcamp.bootcampmanager.user;

import com.bootcamp.bootcampmanager.admin.Admin;
import com.bootcamp.bootcampmanager.admin.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private AdminRepository repository;
    
    @Test
    void userExitsByEmailTest() {
//        Given
        String email = "snow@wall";
        String name = "Jon";
        String surname = "Snow";
        Admin admin = new Admin(name, surname, email, "pass");
        Admin save = repository.save(admin);
//        when
        String firstName = save.getFirstName();
//        Boolean userExists = repository.userExistsByEmail(email);

//        then
        assertThat(firstName).isEqualTo(name);

    }
}