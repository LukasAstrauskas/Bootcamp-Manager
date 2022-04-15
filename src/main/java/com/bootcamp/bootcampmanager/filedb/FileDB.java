package com.bootcamp.bootcampmanager.filedb;

import com.bootcamp.bootcampmanager.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
public class FileDB {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    @Lob
    private byte[] data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    public FileDB(String name, String type, byte[] data, Task task) {
        super();
        this.name = name;
        this.type = type;
        this.data = data;
        this.task = task;
    }
}
