package com.tiy;

import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/22/16.
 */
public class OneAssignmentDataContainer {
    ArrayList<StudentAssignment> studentAssignmentsForThisAssignment;
    int averageForThisAssignment;

    public OneAssignmentDataContainer() {
    }

    public OneAssignmentDataContainer(ArrayList<StudentAssignment> studentAssignmentsForThisAssignment, int averageForThisAssignment) {
        this.studentAssignmentsForThisAssignment = studentAssignmentsForThisAssignment;
        this.averageForThisAssignment = averageForThisAssignment;
    }

    //Getters and Setters
    public ArrayList<StudentAssignment> getStudentAssignmentsForThisAssignment() {
        return studentAssignmentsForThisAssignment;
    }

    public void setStudentAssignmentsForThisAssignment(ArrayList<StudentAssignment> studentAssignmentsForThisAssignment) {
        this.studentAssignmentsForThisAssignment = studentAssignmentsForThisAssignment;
    }

    public int getAverageForThisAssignment() {
        return averageForThisAssignment;
    }

    public void setAverageForThisAssignment(int averageForThisAssignment) {
        this.averageForThisAssignment = averageForThisAssignment;
    }
}
