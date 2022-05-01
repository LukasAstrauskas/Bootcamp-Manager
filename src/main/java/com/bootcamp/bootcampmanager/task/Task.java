package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.filedb.FileDB;
import com.bootcamp.bootcampmanager.studentTaskStatus.StudentTaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @OneToOne( cascade={CascadeType.ALL} , fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileDB fileDB;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    Set<StudentTaskStatus> taskStatusList;

    @ManyToOne
    @JoinColumn(name = "bootcamp_id", referencedColumnName = "id")
    private Bootcamp bootcamp;
}
