package com.tiy;

import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/21/16.
 */
public class StudentAssignmentList {
    ArrayList<StudentAssignment> studentAssignments;

    public StudentAssignmentList() {
    }

    public StudentAssignmentList(ArrayList<StudentAssignment> studentAssignments) {
        this.studentAssignments = studentAssignments;
    }

    public ArrayList<StudentAssignment> getStudentAssignments() {
        return studentAssignments;
    }

    public void setStudentAssignments(ArrayList<StudentAssignment> studentAssignments) {
        this.studentAssignments = studentAssignments;
    }
}
