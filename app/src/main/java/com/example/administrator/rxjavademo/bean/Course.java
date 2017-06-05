package com.example.administrator.rxjavademo.bean;

import java.util.List;

/**
 * Created by chenliu on 2016/4/13.
 * 描述：
 */
public class Course {
    private String id;
    private String name;

    public Course() {
    }

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
