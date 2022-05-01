package com.bootcamp.bootcampmanager.user;

import com.bootcamp.bootcampmanager.admin.Admin;
import com.bootcamp.bootcampmanager.admin.AdminService;
import com.bootcamp.bootcampmanager.config.encoder.Encoder;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import com.bootcamp.bootcampmanager.lecturer.LecturerService;
import com.bootcamp.bootcampmanager.password.PasswordGeneratorService;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final Encoder encoder = new Encoder();

    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final AdminService adminService;
    private final PasswordGeneratorService passwordGeneratorService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, StudentService studentService,
                           LecturerService lecturerService, AdminService adminService,
                           PasswordGeneratorService passwordGeneratorService) {
        this.userRepository = userRepository;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.adminService = adminService;
        this.passwordGeneratorService = passwordGeneratorService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        String password = passwordGeneratorService.generateRandomPassword(user.getEmail());
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        switch (user.getRoles()) {
            case "ROLE_ADMIN":
                adminService.saveAdmin(
                        new Admin(user.getFirstName(), user.getLastName(),
                                user.getEmail(), encodedPassword));
                break;
            case "ROLE_STUDENT":
                studentService.saveStudent(
                        new Student(user.getFirstName(), user.getLastName(),
                                user.getEmail(), encodedPassword));
                break;
            case "ROLE_LECTURER":
                lecturerService.saveLecturer(
                        new Lecturer(user.getFirstName(), user.getLastName(),
                                user.getEmail(), encodedPassword));
                break;
        }
//        MailThread mailThread = new MailThread(mailService, user);
//        mailThread.start();
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserPassword(DataContainer dataContainer) {
        User user = userRepository.getById(dataContainer.getId());
        user.setPassword(encoder.encode(dataContainer.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User userWithNewInfo) {
        User toUpdate = getUserById(userWithNewInfo.getId());
        toUpdate.setFirstName(userWithNewInfo.getFirstName());
        toUpdate.setLastName(userWithNewInfo.getLastName());
        toUpdate.setEmail(userWithNewInfo.getEmail());
        toUpdate.setRoles(userWithNewInfo.getRoles());
        userRepository.save(toUpdate);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Long getUserIDByEmail(String email) {
        return getUserByEmail(email).getId();
    }
}
