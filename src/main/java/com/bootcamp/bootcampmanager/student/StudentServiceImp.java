package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import com.bootcamp.bootcampmanager.lecturer.Lecturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImp implements StudentService {


    private final StudentRepository studentRepository;

    private final BootcampService bootcampService;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository, BootcampService bootcampService) {
        this.studentRepository = studentRepository;
        this.bootcampService = bootcampService;
    }


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public void updateStudent(Student newInfo) {
        Student toUpdate = getStudentById(newInfo.getId());
        toUpdate.setFirstName(newInfo.getFirstName());
        toUpdate.setLastName(newInfo.getLastName());
        toUpdate.setEmail(newInfo.getEmail());
        studentRepository.save(toUpdate);
    }

    @Override
    public void linkStudentToBootcamp(long campID, long studID) {
        Student stud = studentRepository.getById(studID);
        Bootcamp camp = bootcampService.getBootcampById(campID);
        stud.setBootcamp(camp);
        studentRepository.save(stud);
    }

    @Override
    public Student getStudentById(long id) {
        return studentRepository.getById(id);
    }

    @Override
    public void deleteStudentById(long id) {
        this.studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsWithNoBootcamp() {
        List<Student> students = new ArrayList<>();
        for (Student student : getAllStudents()) {
            if (student.getBootcamp() == null) {
                students.add(student);
            }
        }
        return students;
    }

    @Override
    public Long unlinkStudent(long id) {
        Student student = getStudentById(id);
        long bootcampId = student.getBootcamp().getId();
        student.setBootcamp(null);
        saveStudent(student);
        return bootcampId;
    }

    public Student getStudentByEmail(String email) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
        return studentByEmail.get();
    }
}

