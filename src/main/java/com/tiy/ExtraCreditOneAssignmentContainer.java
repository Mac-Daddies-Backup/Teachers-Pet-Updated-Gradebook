package com.tiy;

import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/12/16.
 */
public class ExtraCreditOneAssignmentContainer {
    int extraCreditAmount;
    ArrayList<StudentAssignment> studentAssignments;

    public ExtraCreditOneAssignmentContainer() {
    }

    public ExtraCreditOneAssignmentContainer(int extraCreditAmount, ArrayList<StudentAssignment> studentAssignments) {
        this.extraCreditAmount = extraCreditAmount;
        this.studentAssignments = studentAssignments;
    }

    //Getters and setters
    public int getExtraCreditAmount() {
        return extraCreditAmount;
    }

    public void setExtraCreditAmount(int extraCreditAmount) {
        this.extraCreditAmount = extraCreditAmount;
    }

    public ArrayList<StudentAssignment> getStudentAssignments() {
        return studentAssignments;
    }

    public void setStudentAssignments(ArrayList<StudentAssignment> studentAssignments) {
        this.studentAssignments = studentAssignments;
    }
}
