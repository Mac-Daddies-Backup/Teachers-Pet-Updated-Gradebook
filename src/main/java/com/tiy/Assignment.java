package com.tiy;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;

/**
 * Created by jessicatracy on 10/7/16.
 */
@Entity
@Table(name = "assignments")
public class Assignment {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String dueDate;

    @ManyToOne
    Course course;


    // Constructors
    public Assignment() {
    }

    public Assignment(String name, String dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }

    public Assignment(String name, String dueDate, Course course) {
        this.name = name;
        this.dueDate = dueDate;
        this.course = course;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
