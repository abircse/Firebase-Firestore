package com.thisisabir.firestore.activity.allactivity.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class StudentPojo implements Serializable {

    // here we use exclude for get id only which we create only getter setter not in constructor
    @Exclude private String id;
    private String studentName;
    private String studentDepartment;
    private String studentBatch;

    public StudentPojo() {
        // this is a empty constructor
    }

    public StudentPojo(String studentName, String studentDepartment, String studentBatch) {
        this.studentName = studentName;
        this.studentDepartment = studentDepartment;
        this.studentBatch = studentBatch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentDepartment() {
        return studentDepartment;
    }

    public String getStudentBatch() {
        return studentBatch;
    }
}
