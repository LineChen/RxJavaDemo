package com.example.administrator.rxjavademo.bean;

import java.util.List;

/**
 * Created by chenliu on 2016/4/13.
 * 描述：
 */
public class Student {
    private long id;
    private String name;
    private List<Course> courses;

    public Student() {
    }

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
