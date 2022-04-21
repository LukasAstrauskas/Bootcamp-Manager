package com.bootcamp.bootcampmanager.task;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.student.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FilterContainer {

    static boolean showTable;
    //private long selectedBootcamp;
    private long selectedTask;

    static long taskID;

    public FilterContainer(long selectedTask) {
        //this.selectedBootcamp = selectedBootcamp;
        this.selectedTask = selectedTask;
    }

    public boolean showTable(){
        return this.showTable;
    }

    public void setShow() {
        showTable = true;
    }

    public void unsetShow() {
        showTable = false;
    }

    public long getTask(){
        return taskID;
    }

    public void setId(long id) {
        this.taskID = id;
    }
}
