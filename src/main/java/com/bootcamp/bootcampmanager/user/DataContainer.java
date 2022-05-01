package com.bootcamp.bootcampmanager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class DataContainer {

    private String password;
    private Long id;

    public DataContainer() {
    }

    public DataContainer(Long id) {
        this.id = id;
    }

    public DataContainer(String password, Long id) {
        this.password = password;
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
